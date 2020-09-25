# Introduzione
## Il framework di backend si basa su Spring con la libreria Spring Data REST e Spring BOOT.

* __Spring Data REST__ è una libreria che si basa sui repositories __Spring Data__ e ti permette di esporre in modo semplice i dati del modello.
* __Spring BOOT__ permette di creare dei progetti __Spring__ senza aver necessariamente bisogno di un application server.

Per l'utilizzo di questa tecnologia è _opportuno suddividere il progetto in due sotto-progetti_, uno dei quali conterrà esclusivamente il modello dei dati e la loro rappresentazione.

## Creazione del database

Il primo step per l'avvio di un nuovo progetto è la _creazione della base di dati_, con le relative tabelle, relazioni e permessi. Successivamente si passerà a _creare il modello a partire dalla struttura relazionale già creata_.
Non necessariamente bisogna seguire questo ordine: è infatti anche possibile creare prima il modello dei dati e successivamente generare le tabelle e le relazioni da Spring. In questa guida seguiremo comunque il primo approccio. 

## Creazione del model

Come scritto in precedenza, occorre procedere alla creazione di un progetto dedicato esclusivamente al modello. Questo progetto può essere creato a partire dalla clonazione (con Mercurial) del progetto empty-model, in modo da avere a disposizione il file pom.xml già configurato per risolvere le dipendenze necessarie alla creazione e alla gestione del modello. Le dipendenze dell'empty-model sono:

* __next-spring-data-rest-framework__ - contiene il core del framework nextSDR
  * sarà pertanto necessario effettuare una clonazione anche di questo progetto.
* __jenesis-projection-generator__ - permette di generare in automatico le projection. Le projection sono la modalità di presentazione del dato adottata da Spring Data REST.
  * sarà pertanto necessario effettuare una clonazione anche di questo progetto.
* altre dipendenze necessarie.
  * si tratta in questo caso di dipendenze di terze parti e pertanto sono risolte automaticamente.

Una volta clonato il progetto, è possibile rinominarlo come desiderato e cambiare il _group-id_ e il nome del _package_ che conterrà le entità.

Successivamente è possibile modificare il file __pom.xml__ per configurare il generatore automatico delle projection nel quale indicheremo il percorso nel quale inseriremo le entità e il percorso nel quale saranno generate le projection.

### Creazione delle entià

Le entità vanno generate a partire dallo schema del database, utilizzando tool specifici dell'IDE utilizzato oppure scrivendole manualmente.
Nella creazione delle entità vanno però seguite determinate accortezze:

* inserire come annotazioni di classe `@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})`
* valutare se impostare il __fetch__ type a __lazy__ oppure __eager__ sui campi di chiave esterna;
  * impostando il fetch type a lazy, in fase di richiesta dell'entità le _FK non verranno risolte_
  * impostando il fetch type a eager, in fase di richeista dell'entità le _FK verranno risolte effettuando un join con le tabelle collegate_
* per i campi _ID_ è preferibile utilizzare `@GeneratedValue(strategy = GenerationType.IDENTITY)` visto che il framework prevede questo tipo di funzionamento. Se è necessario utilizzare un'altra strategia di generazione delle PK, bisogna approfondire l'argomento.
* per i campi data, differenziamo il tipo _data_ dal tipo _data e ora_:
  * per i campi di tipo _data_ utilizziamo il tipo __LocalDate__ e inseriamo l'annotazione `@DateTimeFormat` con il pattern desiderato (e.g. yyyy-MM-dd) e l'annotazione `@JsonFormat` configurata come segue `(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")`. La prima annotazione serve a definire il tipo di deserializzazione del dato una volta ricevuto in input, la seconda per la serializzazione in Json in output.
  * per i campi di tipo _data_ e ora utilizziamo il tipo __LocalDateTime__ e inseriamo l'annotazione `@DateTimeFormat` con il pattern `(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")` e l'annotazione `@JsonFormat` configurata come segue `(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")`. La prima annotazione serve a definire il tipo di deserializzazione del dato una volta ricevuto in input, la seconda per la serializzazione in Json in output.

Particolare attenzione va dedicata ai campi FK.

* Nei casi di relazioni __OneToMany__ utilizzare i Set anziché List.
  * utilizzare sui campi Set l'annotazione `@JsonBackReference (value="NOMEUNIVOCO")`. Questa impostazione serve per evitare l'espansione ciclica delle entità.
* Nei casi di relazioni __@ManyToOne__ bisogna indicare:
  * tipologia di cascade (e.g.: `@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})`)
  * annotazione `@JoinColumn`, che indica il nome del campo di fk e il nome del campo pk della tabella target.
   * e.g.: 
```java
    @JoinColumn(name = "id_azienda", referencedColumnName = "id")
```
* Le relazioni manyToMany sono utilizzate nei casi di relazioni molti-a-molti nei quali non si vuole creare l'entità della tabella di cross.
  * Nella prima delle due entità si inserirà:
   * l'annotazione `@ManyToMany`, nella quale indichiamo il tipo di cascade, il tipo di fetch e l'entità target:
e.g.: 
```java
    @ManyToMany(cascade = {CascadeType.ALL,},fetch = FetchType.LAZY,targetEntity = Permesso.class)
```
   * l'annotazione `@JoinTable` nella quale si indicano i dati necessari a effettuare l'operazione di join:
e.g.:  
```java
    @JoinTable(name = "permessi_utente", schema = "gdmjrtest", 
                catalog = "mai_piu_inde",
                joinColumns = @JoinColumn(name = "utente"),
                inverseJoinColumns = @JoinColumn(name = "permesso"))
```
   * l'annotazione `@JsonBackReference` dato che il campo sarà di tipo `Set<EntitàTarget>`.
e.g.: `private Set<Permesso> permessoList`.
  * Nell'altra entità si inserirà un campo di tipo `Set<EntitàOrigine>`  che andrà annotato con l'annotazione `@ManyToMany` per indicare qual'è il campo della prima entità che si riferisce a questa.
e.g.:    
```java
    @ManyToMany(mappedBy = "permessoList", fetch = FetchType.LAZY)
```

#### Cascade in JPA
Il concetto di cascade in __JPA__ differisce da quello di __SQL__. Si indica la tipologia di cascade all'interno dell'attributo cascade nelle annotazioni di relazione. I tipi di cascade possono essere i seguenti:

* __CascadeType.PERSIST__: in fase di inserimento dell'entità, consente di inserire anche una nuova entità collegata dalla FK.
* __CascadeType.MERGE__: in fase di update dell'entità, consente di inserire anche una nuova entità collegata dalla FK.
* __CascadeType.REMOVE__: in fase di cancellazione dell'entità, permette la cancellazione dell'entità collegata dalla FK (probabilmente inutile, non si userà mai)
* __CascadeType.ALL__: specifica tutti i tipi di cascade

#### Orphan Removal
In ogni annotazione in cui si inserisce un cascade e possibile inserire l'attributo orphanRemoval (default a false), che se settato a true farà si che in caso di update in cui l'entità è elemento di una lista, vengano inseriti / aggiornati gli elementi passati nella lista e eliminati gli altri presenti sul DB.
(e.g.: 
```java
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true))
```

> NOTA 1: JPA definisce altri tipi di cascade che per adesso non sembrano utili ai nostri scopi.
> NOTA 2: La combinazione di _orphanRemoval = true_ e _CascadeType.ALL_ o _CascadeType.REMOVE_ produce degli effetti strani, meglio non farlo.

### Configurazione del generatore di projection

Introduzione
Una _Projection_ è una modalità di presentazione del dato adottata da __Spring Data REST__. Tramite una projection è possibile, a partire da un'entità, decidere quali dati esporre su una chiamata REST.
Il generatore di projection _jenesis-projections-generator_ permette di generare automaticamente, a partire dalle entità, tutte le possibili projection. Ad esempio, a partire da un'entità con 3 diverse foreign key, verranno generate 8 projection che coprono le varie combinazioni di espansioni delle entità correlate. Verrà quindi generata una projection senza foreign key e solo con i dati base, una che conterrà la risoluzione della prima foreign key, una con la prima e la seconda, una con solo la seconda, ecc. Ovviamente ogni classe projection può essere estesa nel progetto per ridefinire o aggiungere metodi.
In più è possibile anche decidere qualle projection generare. Per farlo si usa la annotazione `@GenerateProjections({"attivitaFattaList", "ecc."})` specificando al uso interno quali campi mettere nella projection. Se non c'e' l'annotazione vengono generate tutte.

Come utilizzare il generatore
Il generatore si configura all'interno del file pom.xml del progetto del modello, all'interno del tag configuration del tag artifactId jenesis-projections-generator.
```
    <plugin>
        <groupId>jenesisprojections.generator</groupId>
        <artifactId>jenesis-projections-generator</artifactId>
        <version>1.2-SNAPSHOT</version>
        <executions>
            <execution>
                <phase>generate-sources</phase>
                <goals>
                    <goal>generateProjectionsSource</goal>
                </goals>
                <configuration>
                    <title>Jenesis 4 Java</title>
                    <relativeTargetPackage>projections.generated</relativeTargetPackage>
                    <outputJavaDirectory>target/generated-sources/java</outputJavaDirectory>
                    <entitiesPackageStartNode>it.bologna.ausl.model.entities</entitiesPackageStartNode>
                </configuration>
            </execution>
        </executions>
    </plugin>
```
* __relativeTargetPackage__: inseriamo il package relativo all'interno del quale vogliamo che vengano inserite le projection generate. Il package completo sarà formato dal package dell'entità concatenato a quello relativo inserito
* __outputJavaDirectory__: inseriamo la cartella vera e propria che dovrà contenere i file generati (se lasciamo il valore di default, le projections saranno generate all'interno della sezione generated-source relativa al codice generato in automatico. Suggeriamo di lasciare questo valore di default.)
* __entitiesPackageStartNode__: inseriamo il package all'interno del quale si trovano le entità.

>NOTE:
>* le projection vengono generate ogni volta che viene lanciato il comando Maven "install".
>  * Su __NetBeans__ di default questa operazione corrisponde al comando ```Build/Clean & Build```

#### Creazione di una nuova projection

In alcuni casi si può avere la necessità di creare una __Projection__ personalizzata, da usare in aggiunta o in sostituzione di quelle generate automaticamente dal generatore.
Una projection è definita come un'interfaccia con l'annotazione `@Projection` che ne specifica il nome e l'entità dalla quale deriva.

E.g.:
```java
  @Projection(name = "UtentiBase", types = { Utente.class })
  public interface UtentiBase { 
      
      public String getUsername();
      
      public String getNome();
      
      public String getCognome();
      
      public Struttura getStruttura();

}    
```
Dato che il generatore genera tutte le projection base, è possibile definirne delle nuove estendendo quelle generate. Così facendo è possibile anche ridefinire la visibilità di determinati attributi.
```java
  @Projection(name = "UtentiBaseNoUserName ", types = { Utente.class })
  public interface UtentiBaseNoUserName extends UtentiBase{ 

      @Value("#{null}")
      @Override
      public String getUsername();
  }
```
In questo esempio, utilizzando la Projection definita in questo modo, viene nascosto il valore del campo _username_.

## Servizi REST

Per la parte dedicata ai servizi REST occorre creare, come detto precedentemente, un'altra applicazione Spring Boot.
Un servizio particolarmente utile per creare un progetto Spring Boot è messo a disposizione su [Spring](https://start.spring.io).

Lo zip prodotto da questo servizio conterrà un file pom.xml con le dipendenze desiderate, alle quali vanno aggiunte le dipendenze del progetto del modello e del framework.

Queste sono le ulteriori dipendenze base da inserire (è possibile ovviamente inserire in aggiunta tutte quelle che si desiderano):

__JPA__ | __DevTools__ | __Rest Repositories__ | __PostgreSQL__
------- | ------------ | --------------------- | --------------


> NOTE:
>* Al posto di PostgreSQL va indicato il driver apposito in base al DBMS utilizzato

### Configurazione REST

__Configurazione paginazione__
E' opportuno creare una classe dedicata alla configurazione del paginatore di Spring. In questo modo è possibile modificare il comportamento di default (ad esempio numero di elementi restituiti per pagina)

E.g.:
```java
@Configuration
public class PageableConfiguration implements WebMvcConfigurer {

    static final Pageable DEFAULT_PAGE_REQUEST = PageRequest.of(0, 20);

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setFallbackPageable(DEFAULT_PAGE_REQUEST);
        argumentResolvers.add(resolver);
    }
}
```
__Configurazione REST__
E' opportuno creare una classe dedicata alla configurazione dei servzi REST. E' essenziale inserire i metodi sotto indicati: il primo consente le chiamate interdominio, il secondo consente di utilizzare le projection nelle risposte dei metodi che restituiscono delle entità.
```java
@Configuration
public class RestConfiguration {

    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // you USUALLY want this
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("PATCH");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public SpelAwareProxyProjectionFactory projectionFactory() {
        return new SpelAwareProxyProjectionFactory();
    }
}
```

### Repository

La repository è un'interfaccia specifica di Spring Data REST riferita ad una entità, che permette di interagire con il database per gestire i dati di quella specifica entità. Va creata un'interfaccia repository per ogni entità.
Attraverso i repository di ogni entità sarà quindi possibile avere a disposizione metodi per effettuare il salvataggio a DB dei dati, inserimenti, eliminazioni, e letture.

E.g.:
```java
  @RepositoryRestResource(collectionResourceRel = "albo", path = "albo", exported = false, excerptProjection = AlboWithPlainFields.class)
  public interface AlboRepository extends
          NextSdrQueryDslRepository<Albo, Integer, QAlbo>,
          JpaRepository<Albo, Integer> {
  }
```
E' opportuno impostare l'attributo exported come false per evitare che l'entità venga esportata in automatico da SDR.

Ai fini del funzionamento del framework, è necessario inserire l'annotazione aggiuntiva ```@NextSdrRepository(repositoryPath = "path", baseUrl="/il/base/url", defaultProjection = NoneProjection.class)``` .
Nella proprietà repositoryPath indicare il path sul quale è mappata l'entità sul controller (vedi capitolo CTRL - Controller). Per convenzione mettiamo il nome dell'entità in stampatello minuscolo, senza spazi e senza underscore o trattini.
Nella proprietà repositoryPath indicare il baseUrl sulla quale l'entità deve essere mappata (deve corrispondere a quella sul controller).
Nella proprietà defaultProjection indichiamo la projection di default con la quale l'entità sarà presentata al client quando richiesta.

Questa interfaccia estende:

* __JpaRepository__, che ha dei metodi standard per la manipolazione delle entità.
  * Le due classi indicate quando si estende JpaRepository indicano
    * La classe entità
    * La classe relativa al tipo della PK dell'entità
  * Nota: In alternativa può essere usato anche PagingAndSortingRepository che ha lo stesso scopo di JpaRepository e che a Francesco piace di più come nome
* __NextSdrQueryDslRepository__, una classe del framework che personalizza i filtri sulle query. Eventualmente è possibile estendere __CustomQueryDslRepository__ con la propria implementazione ed utilizzare quella.
  * Le tre classi indicate quando si estende __CustomQueryDslRepository__ indicano
    * La classe entità
    * La classe relativa al tipo della PK dell'entità
    * La classe di __QueryDSL__ relativa all'entità (in genere coincide con il nome della classe dell'entità preceduta da una __Q__)

### Controller

Il controller è una classe nella quale vengono mappati i metodi che risponderanno alle chiamate HTTP.

__RestControllerEngine__
I controller fanno uso di un'interfaccia __RestControllerEngine__ di cui è necessario fornire un'implementazione nel progetto di sviluppo.
Nell'implementazione fornita è possibile effettuare l'override di alcuni metodi, tra cui quelli che servono per definire come reperire un'entità a partire dalla sua chiave.
Di seguito un esempio di implementazione di RestControllerEngine.

```java
  @Service
  public class RestControllerEngineImpl extends RestControllerEngine {

      @PersistenceContext
      EntityManager entityManager;

      @Override
      protected Object retriveEntity(Class entityClass, Object entityKey) {
          return entityManager.find(entityClass, ((Integer)entityKey).longValue());
      }

      @Override
      protected boolean isKeysEquals(Object key1, Object key2) {
          return super.isKeysEquals(key1, key2);
      }
  }
```

__Implementazione dei Controller__

Nel progetto di sviluppo è necessario creare due classi di controller: uno per la gestione delle chiamate __GET__ e l'altro per la gestione delle altre tipologie di chiamate.

_Controller per chiamate GET_
É necessario creare una classe che mappi le __URL__ per le chiamate __GET__. La classe deve avere in autowiring la nostra implementazione del RestControllerEngine di cui può usare i metodi per recuperare le risorse.
In questo controller per ogni entità andrà implementato un metodo come quello riportato nell'esempio sotto.

```java
  @RestController
  @RequestMapping(value = "${nextsdr.base-path-prefix}") // @RequestMapping(value = "/api")
  public class RestBaseGetController  {

      @Autowired
      private RestControllerEngineImpl restControllerEngine;


      @RequestMapping(value = {"utenti", "utenti/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
      public ResponseEntity<?> utenti(
              @QuerydslPredicate(root = Utente.class) Predicate predicate,
              Pageable pageable,
              @RequestParam(required = false) String projection,
              @PathVariable(required = false) Object id,
              HttpServletRequest request,
              @RequestParam(required = false, name = "additionalData") String additionalData) throws ClassNotFoundException, EntityReflectionException, IllegalArgumentException, IllegalAccessException, RestControllerEngineException, AbortLoadInterceptorException {

          Object resource = restControllerEngine.getResources(request, id, projection, predicate, pageable, additionalData, QUtente.utente, Utente.class);
          return ResponseEntity.ok(resource);
      }
  }
```

_Controller per chiamate non GET_
Il controller per le chiamate __non GET__ deve estendere la classe astratta _BaseCrudController_ che contiene i metodi base per l'interfacciamento con i repository e fornire l'override del metodo get per ottenere la nostra implementazione del controller engine.
Non è necessario mappare Update, Insert e Delete. Nel caso si volessero dei comportamenti personalizzati se ne può fare l'override.
Es.
```java
  @RestController
  @RequestMapping(value = "${nextsdr.base-path-prefix}")
  public class RestBaseCUDController extends BaseCrudController {

      @Autowired
      private RestControllerEngineImpl restControllerEngine;

      @Override
      public RestControllerEngine getRestControllerEngine() {
          return restControllerEngine;
      }
  }
```

### Interceptor

__Introduzione__
Un interceptor permette di intercettare le operazioni sulle entità, sia in fase di lettura che di scrittura. Al contrario degli altri oggetti descritti precedentemente (Projection, Repository e Controller), il concetto di Interceptor non è previsto di default in Spring Data REST ma è stato introdotto nel framework nextSDR.
Gli utilizzi tipici degli interceptor possono essere i seguenti:

* inibire la visualizzazione o la modifica di alcuni dati ad utenti che non ne hanno il permesso, secondo delle logiche di autorizzazione specifiche dell'applicazione in sviluppo.
* ottenere dei filtri personalizzati che normalmente non sono messi a disposizione da Spring Data REST
* eseguire delle Post Operazioni in seguito all'inserimento/modifica/eliminazione di alcune entità (es. aggiornamento del campo "totale" dell'entità ordine a seguito dell'inserimento di una nuova entità RigaOrdine)

__Creazione di un'interceptor__
Gli interceptor agiscono sulle entità, pertanto devono essere basati su un'entità di riferimento. É possibile creare più interceptor sulla stessa entità: in tal caso le operazioni in essi definite agiranno in successione.

Per creare un'interceptor è necessario creare una classe con le annotazioni ```@Component```  e ```@NextSdrInterceptor(name = "NOME-INTERCEPTOR")``` 
La classe deve implementare l'interfaccia _NextSdrControllerInterceptor_, che obbliga ad implementare i seguenti metodi:

* ```public Class getTargetEntityClass()```;
  * nel quale si deve restiture la classe dell'entità alla quale l'interceptor fa riferimento.
* ```public Predicate beforeSelectQueryInterceptor(Predicate initialPredicate, Map<String, String> additionalData, HttpServletRequest request);```
  * viene richiamato prima dell'esecuzione della query di select; consente di tornare il predicato che deve essere inserito nella where conditio n.
    I parametri sono:
    * initialPredicate: predicato attuale; è possibile utilizzarlo per specificare condizioni aggiuntive (in and e or) per ottenere il predicato di ritorno. Può essere anche ignorato per specificare un predicato di ritorno che non tiene conto di eventuali condizioni già specificate.
    * additionalData: eventuali dati addizionali che è possibile specificare nella queryString dell'URL con cui viene richiamato il servizio REST.
    * request: oggetto che contiene la richiesta HTTP
* ```public List<Object> afterSelectQueryInterceptor(List<Object> entities, Map<String, String> additionalData, HttpServletRequest request);```
  * viene richiamato dopo l'esecuzione della query di select nel caso in cui tale esecuzione torni una lista di entità. Consente di tornare la lista di entità eventualmente modificata.
    I parametri sono (si indicano solo i parametri differenti rispetto a quelli già descritti su):
    * entities: lista di entità restituite dalla query corrispondente al servizio REST richiamato;
* ```public Object afterSelectQueryInterceptor(Object entity, Map<String, String> additionalData, HttpServletRequest request);```
  * viene richiamato dopo l'esecuzione della query di select nel caso in cui tale esecuzione torni un'unica entità. Consente di tornare l'entità eventualmente modificata.
    Si consiglia di implementare la logica della modifica dell'entità in questo metodo ed eventualmente richiamarla su ogni entità della lista utilizzata nel metodo precedente.
    I parametri sono (si indicano solo i parametri differenti rispetto a quelli già descritti su):
    * entity: entità restituita dalla query corrispondente al servizio REST richiamato;
* ```public Object beforeCreateEntityInterceptor(Object entity, Map<String, String> additionalData, HttpServletRequest request) throws RollBackInterceptorException;```
  * viene richiamato prima dell'inserimento a DB di una entità. Consente di tornare l'entità eventualmente modificata, consentendo quindi di apportare modifiche ai dati che verranno inseriti.
  * è possibile lanciare l'eccezione RollBackInterceptorException per inibire l'inserimento.
  I parametri sono (si indicano solo i parametri differenti rispetto a quelli già descritti su):
    * entity: entità che sta per essere inserita
* ```public Object beforeUpdateEntityInterceptor(Object entity, Map<String, String> additionalData, HttpServletRequest request) throws RollBackInterceptorException;```
  * viene richiamato prima dell'aggiornamento a DB dei dati di una entità. Consente di tornare l'entità eventualmente modificata, consentendo quindi di apportare modifiche ai dati che verranno aggiornati.
  * è possibile lanciare l'eccezione RollBackInterceptorException per inibire l'aggiornamento.
  I parametri sono (si indicano solo i parametri differenti rispetto a quelli già descritti su):
    * entity: entità che sta per essere aggiornata
* ```public void beforeDeleteEntityInterceptor(Object entity, Map<String, String> additionalData, HttpServletRequest request) throws RollBackInterceptorException;```
  * viene richiamato prima dell'eliminazione a DB di una entità.
  * è possibile lanciare l'eccezione RollBackInterceptorException per inibire la cancellazione.
  I parametri sono (si indicano solo i parametri differenti rispetto a quelli già descritti su):
    * entity: entità che sta per essere cancellata

Si consiglia di estendere la classe NextSdrEmptyControllerInterceptor che implementa già tutti i metodi previsti dall'interfaccia ad eccezione naturalmente di getTargetEntityClass, ed eseguire l'override solo per i metodi di interesse.

>NOTE:
>* i metodi beforeSelectQueryInterceptor e i due afterSelectQueryInterceptor vengono richiamati anche nel caso in cui l'entità viene richiamata in espansione tramite le projection 
>    * Tale comportamento è valido sempre per le projectin auto-generate, per quelle personalizzate è necessario gestirlo specificamente
>    * Es. se viene richiesto un servizio REST per leggere i dati dell'entità Ordine e viene utilizzata una sue projection che espande l'entità figlia RigaOrdine, i metodi sopra indicati dell'interceptor verranno richiamati per entrambe le entità. Sarà, quindi, possibile evitare di mostrare alcune RigheOrdine in base a determinate condizioni di Autorizzazione relative all'utente che ha effettuato la richiesta.
>* i metodi beforeCreateEntityInterceptor e beforeUpdateEntityInterceptor  vengono richiamati anche nel caso in cui la richiesta REST di inserimento/aggiornamento di un'entità prevede l'inserimento/aggiornamento anche di un'altra entità correlata.
>   * Es. se viene richiamato un servizio REST per inserire un'entità Ordine, che nella sua definizione presenta anche una lista di RigaOrdine popolata con una serie di nuove righe ordine da inserire o aggiornare, i metodi sopra indicati dell'interceptor verranno richiamati per entrambe le entità.


Di seugito un esempio di Interceptor creato per l'entità _"Struttura"_
```java
  @Component
  @NextSdrInterceptor(name = "struttura-interceptorTest")
  public class StrutturaInterceptor extends NextSdrEmptyControllerInterceptor {

      @Override
      public Class getTargetEntityClass() {
          return Struttura.class;
      }

      @Override
      public Predicate beforeSelectQueryInterceptor(Predicate initialPredicate, Map<String, String> additionalData, HttpServletRequest request, boolean mainEntity, Class projectionClass) throws AbortLoadInterceptorException {
          // Recupero l'utente loggato
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          Utente utente = (Utente) authentication.getPrincipal();

          return QStruttura.utenteStruttura.utente.id.eq(utente.getId()).and(initialPredicate);
      }

      @Override
      public Object afterSelectQueryInterceptor(Object entity, Map<String, String> additionalData, HttpServletRequest request, boolean mainEntity, Class projectionClass) throws AbortLoadInterceptorException {
          // Recupero l'utente loggato
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          Utente utente = (Utente) authentication.getPrincipal();
          
          Struttura struttura = (Struttura) entity;
          if (utente.Ruolo == Ruoli.Administrator)
              return struttura;
          else
              return null;
      }

      @Override
      public Object beforeCreateEntityInterceptor(Object entity, Map<String, String> additionalData, HttpServletRequest request, boolean mainEntity, Class projectionClass) throws AbortSaveInterceptorException {
          Struttura struttura = (Struttura) entity;
          // ...... eventuali modifiche all'entità o a entità ad essa correlate
          return struttura;
      }

      @Override
      public Object beforeUpdateEntityInterceptor(Object entity, Object beforeUpdateEntity, Map<String, String> additionalData, HttpServletRequest request, boolean mainEntity, Class projectionClass) throws AbortSaveInterceptorException {
          System.out.println("in: beforeUpdateInterceptor di " + entity.getClass().getSimpleName());
          Struttura struttura = (Struttura) entity;
          // ...... eventuali modifiche all'entità o a entità ad essa correlate
          return struttura;
      }
  }
```

### Sintassi delle chiamate REST

__Sintassi della chiamata__

Per utilizzare il framework si devono effettuare delle chiamate REST all'indirizzo dell'applicazione (e.g. `http://localhost:<port>`) seguito dal nome dell'entità che sulla qualse si vuole agire. Il nome dell'entità è quello settato all'interno dell'attributo __path__ nel repository. (Per convenzione deve essere il nome dell'entità in stampatello piccolo e tutto attaccato, senza '_' e senza '-' ).



_Chiamate in lettura_
La chiamata base in __GET__ torna l'elenco delle entità, come per esempio `http://localhost:10005/utente` ritorna tutti gli utenti.
Per ottenere uan singola entità, conoscendone l'ID, è possibile effettuare la seguente chiamata:
`http://localhost:10005[baseUrl]/utente/123`

E possibile applicare dei filtri inserendo il nome dell'attributo nella query string seguito dal carattere '=' e il valore che si vuole filtrare (a parte il caso con le stringhe che sarà descritto sotto).
Se nel filtro è passato due volte lo stesso campo, allora verrà effettuato un or tra i due valori (a parte nel caso delle date nel quale viene creato un between).

Filtri sui numeri: attualmente è possibile filtrare solo per uguaglianza; ad esempio `http://localhost:10005/utente?matricola=123`

Filtri sulle strighe: è possibile filtrare con questi criteri

* __equals__
* __equalsIgnoreCase__
* __startsWith__
* __startsWithIgnoreCase__
* __contains__
* __containsIgnoreCase__

un esempio è il seguente: : `http://localhost:10005[baseUrl]/utente?nome=$equals(Giuseppe)`

Filtri sulle date: si filtra sulle date con il seguente formato `http://localhost:10005/utente?dataNascita=<formato specificato nell'entità, con annotazione @DateTimeFormat>`

Nella query string è possibile passare un parametro projection con il nome della projection che si vuole applicare al risultato.
`http://localhost:10005[baseUrl]/utente?matricola=123&projection=UtentiWithOrdini`

L'additionalData è un parametro che raccoglie gli eventuali parametri aggiuntivi che è possibile leggere negli interceptor. Il suo formato è il seguente:
>param1=val1,param2=val2,... ad esempio
`http://localhost:10005[baseUrl]/utente?matricola=123&projection=UtentiWithOrdini&additionalData=param1=val1,param2=val2`

__Tutti questi parametri sono combinabili tra loro, compresi i filtri (saranno valutati in AND)__



_Chiamate in scrittura_
Esistono tre tipi di scrittura

* __INSERT__
per effettuare un insert eseguire una chiamata __PUT__ o __POST__, passando nel body il json dell'entità da inserire.
Si possono presentare le seguenti situazioni

  1. Inserimento di un'entità nella cui FK si vuole indicare un'entità già esistente
    URL: `http://localhost:10005[baseUrl]/utente?additionalData=param1=val1,param2=val2`
    BODY:
    ```json
        {
          "username": "DRNNGN75L61Z306I",
          "nome": null,
          "descrizione": null,
          "passwordHash": "false",
          "idRuoloAziendale": 1,
          "idInquadramento": null,
          "fk_struttura": {
              "id": 376164
          }
        }
    ```
  2. Inserimento di un'entità nella quale si vuole inserire anche un'entità correlata
    URL: `http://localhost:10005[baseUrl]/utente?additionalData=param1=val1,param2=val2`
    BODY:
    ```json
        {
          "username": "DRNNGN75L61Z306I",
          "nome": null,
          "descrizione": null,
          "passwordHash": "false",
          "idRuoloAziendale": 1,
          "idInquadramento": null,
          "struttura": {
              "nome": "nomeNuovo",
              "dataApertura": "2018-07-26"
          }
        }
    ```
  3. Inserimento di un'entità nella quale si vuole inserire anche una lista di entità correlate
    URL:`http://localhost:10005[baseUrl]/utente?additionalData=param1=val1,param2=val2`
    BODY:
    ```json
        {
        "username": "DRNNGN75L61Z306I",
        "nome": null,
        "descrizione": null,
        "passwordHash": "false",
        "idRuoloAziendale": 1,
        "idInquadramento": null,
        "attivitaList": [
            {
                "nome":"attivita1",
                "data": "2018-07-26"
            },
            {
                "id": 2,
                "nome": "attivita1",
                "data": "2018-07-26"
            }
        }
    ```  

* __UPDATE__
Per eseguire l'aggiornamento occorre eseguire una chiamata PATCH; si possono presentare le seguenti situazioni:
E' possibile passara solamente i campi da aggiornare, nel caso si voglia settare null un campo, si deve passare il valore null.

  1. Aggiornamento di un'entità nella quale si vuole cambiare la FK aggiornandola con un'entità esistente
    URL: `http://localhost:10005[baseUrl]/utente/<id>?additionalData=param1=val1,param2=val2`
    BODY:
    ```json
        {
          "nome": "nuovoNome",
          "descrizione": null,
          "fk_struttura": {
              "id": 376165
          }
        }
    ```
  2. aggiornamento di un'entità nella quale si vuole inserire una nuova entità e settarla come correlata
    URL: `http://localhost:10005[baseUrl]/utente/<id>?additionalData=param1=val1,param2=val2`
    BODY:
    ```json
        {
          "username": "DRNNGN75L61Z306I",
          "nome": "nuovoNome",
          "descrizione": null,
          "struttura": {
              "nome": "nomeNuovo",
              "dataApertura": "2018-07-26"
          }
        }
    ```
  3. aggiornamento di un'entità nella quale si aggiornare anche un'entità correlata già esistente
    URL: `http://localhost:10005[baseUrl]/utente/<id>?additionalData=param1=val1,param2=val2`
    BODY:
    ```json
        {
          "username": "DRNNGN75L61Z306I",
          "nome": "nuovoNome",
          "descrizione": null,
          "struttura": {
              "id":12345,
              "nome": "nomeAggiornato"
          }
        }
    ```
  4. aggiornamento di un'entità nella quale si vuole inserire/aggiornale/cancellare anche una lista di entità correlate
    URL: `http://localhost:10005[baseUrl]/utente?additionalData=param1=val1,param2=val2`
    BODY:
    ```json
        {
        "username": "DRNNGN75L61Z306I",
        "nome": null,
        "descrizione": null,
        "passwordHash": "false",
        "idRuoloAziendale": 1,
        "idInquadramento": null,
        "attivitaList": [
            {
                "nome": "attivita1",
                "data": "2018-07-26"
            },
            {
                "id": 2,
                "nome": "attivita1",
                "data": "2018-07-26"
            }
        }
    ```
* Se le attività di attivitaList hanno id seriale autogenerato, la prima attività della lista attivitaList sarà inserita, mentre la seconda modificata.
* Se le attività di attivitaList hanno id non autogenerato si avrebbe un errore; in questo caso è necessario indicare sempre l'id. Se questo esiste l'entità viene modificata, altrimenti viene inserita.

>NB: è possibile far si che tutte le altre entità attività relative all'utente, presenti sul db siano cancellate. Per farlo bisogna impostare nell'annotazione _OneToMany_ sull'utente la proprietà _orphanRemoval_ a __true__ (vedi capitolo ENTITA - Creazione delle entità) per maggiori informazioni.

* __DELETE__
  Per eseguire la cancellazione occorre eseguire una chiamata __DELETE__ 
  URL: `http://localhost:10005[baseUrl]/utente/<id>?additionalData=param1=val1,param2=val2`

* __BATCH__
  Le istruzioni batch si ottengono  eseguendo una chiamata __POST__ con la sintassi seguente:
  URL: `http://localhost:10005[baseUrl]/batch`
  BODY:
  ```json
      [{
              "id": 17,             //(solo nel caso di UPDATE o DELETE)
              "operation": "INSERT/UPDATE/DELETE",
              "entityPath": "[baseUrl]/azienda",
              "additionalData": {             //opzionale
                  "param1": "val1",
                  "param2": "val2"
              },
              "entityBody": {        //corpo dell'entità
                  "aoo": "222222",
                  "ribaltaArgo": true,
                  "codiceRegione": "080",
                  "nome": "AOSPFE",
                  "descrizione": "gjjhjjdmgdmgdm",
                  "ribaltaInternauta": false
              }
          },
          ...
      ]
  ```

__Sintassi della risposta__
In caso di chiamate in lettura, la risposta tornata dal server è espressa nel seguente formato __JSON__:
```json
    {
        "_embedded": {
            "utente": [
                {
                    "id": 333431,
                    "username": "RossiNadia",
                    "nome": null,
                    "descrizione": null,
                    "idRuoloAziendale": 1,
                    "idInquadramento": null,
                    "fk_struttura": {
                        "id": 333431,
                        "targetEntity": "struttura",
                        "url": "http://localhost:10005/struttura?id=333431"
                    }
                }
            ]
        },
        "_links": {
            "first": {
                "href": "http://localhost:10005/utente?page=0&size=1"
            },
            "self": {
                "href": "http://localhost:10005/utente?page=0&size=1"
            },
            "next": {
                "href": "http://localhost:10005/utente?page=1&size=1"
            },
            "last": {
                "href": "http://localhost:10005/utente?page=39960&size=1"
            }
        },
        "page": {
            "size": 1,
            "totalElements": 39961,
            "totalPages": 39961,
            "number": 0
        }
    }
```

Se si richiede un'entità singola, della quale si conosce l'ID, si otterrà un JSON nel seguente formato:
```json
    {
        "id": 333431,
        "username": "RossiNadia",
        "nome": null,
        "descrizione": null,
        "idRuoloAziendale": 1,
        "idInquadramento": null,
        "fk_struttura": {
            "id": 333431,
            "targetEntity": "struttura",
            "url": "http://localhost:10005/struttura?id=333431"
        }
    }
```
Tutte le chiamate in scrittura torneranno l'entità che si è passata come parametro alla quale viene applicata la projection base; questa includerà le eventuali modifiche attuate dall'interceptor. Le chiamate __BATCH__ e __DELETE__ non tornano nulla.

### babel-framework-backend

Si compili nell'ordine:
- next-spring-data-rest-framework
- jenesis-projections-generator
- internauta-utils
- internauta-model
- black-box
- internauta-service
