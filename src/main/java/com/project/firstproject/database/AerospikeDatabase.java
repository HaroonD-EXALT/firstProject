package com.project.firstproject.database;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.Statement;
import com.aerospike.mapper.tools.AeroMapper;


public class AerospikeDatabase {

    private static AerospikeDatabase database;
    private static AerospikeClient aerospikeClient;

    private static final String NAMESPACE = "test";
    private Statement stmt;

    AeroMapper mapper;

    private static WritePolicy writePolicy;

    public AerospikeClient getAerospikeClient() {
        return aerospikeClient;
    }


    private AerospikeDatabase() {
        aerospikeClient = new AerospikeClient("localhost", 3000);
        writePolicy = new WritePolicy();
        stmt = new Statement();
        stmt.setNamespace(NAMESPACE);
        mapper = new AeroMapper.Builder(aerospikeClient).build();
    }

    public static AerospikeDatabase getInstance() {
        if (aerospikeClient == null) {
            database = new AerospikeDatabase();
            return database;
        }
        return database;

    }



    public  WritePolicy getWritePolicy() {
        return writePolicy;
    }

    public Statement getStmt() {
        return stmt;
    }

    public String getNAMESPACE() {
        return NAMESPACE;
    }

    public AeroMapper getMapper() {
        return mapper;
    }





}
