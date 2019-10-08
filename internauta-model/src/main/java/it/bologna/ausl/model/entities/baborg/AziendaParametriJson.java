package it.bologna.ausl.model.entities.baborg;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author gdm
 */
public class AziendaParametriJson implements Serializable {
    private String babelSuiteBdsToolsUrl;
    private String babelSuiteWebApiUrl;
    private String descBreve;
    private String loginSSOField;
    private String loginDBField;
    private String loginDBFieldBaborg;
    private String shalboApiUrl;
    private MasterChefParmas masterchefParams;
    private String entityId;
    private String loginPath;
    private String crossLoginUrlTemplate;
    private MongoParams mongoParams;
    private String logoutUrl;
    //private String mongoConnectionString;



    public AziendaParametriJson() {
    }

    public AziendaParametriJson(String babelSuiteBdsToolsUrl, String babelSuiteWebApiUrl, String descBreve, String loginSSOField, String loginDBField, String loginDBFieldBaborg, String shalboApiUrl, MasterChefParmas masterchefParams, String entityId, String loginPath, String crossLoginUrlTemplate, MongoParams mongoParams, String logoutUrl) {
        this.babelSuiteBdsToolsUrl = babelSuiteBdsToolsUrl;
        this.babelSuiteWebApiUrl = babelSuiteWebApiUrl;
        this.descBreve = descBreve;
        this.loginSSOField = loginSSOField;
        this.loginDBField = loginDBField;
        this.loginDBFieldBaborg = loginDBFieldBaborg;
        this.shalboApiUrl = shalboApiUrl;
        this.masterchefParams = masterchefParams;
        this.entityId = entityId;
        this.loginPath = loginPath;
        this.crossLoginUrlTemplate = crossLoginUrlTemplate;
        this.mongoParams = mongoParams;
        this.logoutUrl = logoutUrl;
    }

    public String getBabelSuiteBdsToolsUrl() {
        return babelSuiteBdsToolsUrl;
    }

    public void setBabelSuiteBdsToolsUrl(String babelSuiteBdsToolsUrl) {
        this.babelSuiteBdsToolsUrl = babelSuiteBdsToolsUrl;
    }

    public String getBabelSuiteWebApiUrl() {
        return babelSuiteWebApiUrl;
    }

    public void setBabelSuiteWebApiUrl(String babelSuiteWebApiUrl) {
        this.babelSuiteWebApiUrl = babelSuiteWebApiUrl;
    }
    
        public String getDescBreve() {
        return descBreve;
    }

    public void setDescBreve(String descBreve) {
        this.descBreve = descBreve;
    }

    public String getLoginSSOField() {
        return loginSSOField;
    }

    public void setLoginSSOField(String loginSSOField) {
        this.loginSSOField = loginSSOField;
    }

    public String getLoginDBField() {
        return loginDBField;
    }

    public void setLoginDBField(String loginDBField) {
        this.loginDBField = loginDBField;
    }

    public String getLoginDBFieldBaborg() {
        return loginDBFieldBaborg;
    }

    public void setLoginDBFieldBaborg(String loginDBFieldBaborg) {
        this.loginDBFieldBaborg = loginDBFieldBaborg;
    }

    public String getShalboApiUrl() {
        return shalboApiUrl;
    }

    public void setShalboApiUrl(String shalboApiUrl) {
        this.shalboApiUrl = shalboApiUrl;
    }

    public MasterChefParmas getMasterchefParams() {
        return masterchefParams;
    }

    public void setMasterchefParams(MasterChefParmas masterchefParams) {
        this.masterchefParams = masterchefParams;
    }

    public MongoParams getMongoParams() {
        return mongoParams;
    }

    public void setMongoParams(MongoParams mongoParams) {
        this.mongoParams = mongoParams;
    }
    
    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getLoginPath() {
        return loginPath;
    }

    public void setLoginPath(String loginPath) {
        this.loginPath = loginPath;
    }

    public String getCrossLoginUrlTemplate() {
        return crossLoginUrlTemplate;
    }

    public void setCrossLoginUrlTemplate(String crossLoginUrlTemplate) {
        this.crossLoginUrlTemplate = crossLoginUrlTemplate;
    }
    
    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }
    
//    public String getMongoConnectionString() {
//        return mongoConnectionString;
//    }
//
//    public void setMongoConnectionString(String mongoConnectionString) {
//        this.mongoConnectionString = mongoConnectionString;
//    }

    public static AziendaParametriJson parse(ObjectMapper objectMapper, String src) throws IOException {
        return objectMapper.readValue(src, AziendaParametriJson.class);
    }
    
    public class MasterChefParmas {
        private String redisHost;
        private Integer redisPort;
        private String inQueue;
        

        public MasterChefParmas() {
        }

        public MasterChefParmas(String redisHost, Integer redisPort, String inQueue) {
            this.redisHost = redisHost;
            this.redisPort = redisPort;
            this.inQueue = inQueue;
        }

        public String getRedisHost() {
            return redisHost;
        }

        public void setRedisHost(String redisHost) {
            this.redisHost = redisHost;
        }

        public Integer getRedisPort() {
            return redisPort;
        }

        public void setRedisPort(Integer redisPort) {
            this.redisPort = redisPort;
        }

        public String getInQueue() {
            return inQueue;
        }

        public void setInQueue(String inQueue) {
            this.inQueue = inQueue;
        }
    }
    
    public class MongoParams {
        private String connectionString;
        private String root;

        public MongoParams() {
        }

        public MongoParams(String connectionString, String root) {
            this.connectionString = connectionString;
            this.root = root;
        }

        public String getConnectionString() {
            return connectionString;
        }

        public void setConnectionString(String connectionString) {
            this.connectionString = connectionString;
        }

        public String getRoot() {
            return root;
        }

        public void setRoot(String root) {
            this.root = root;
        }
    }
}