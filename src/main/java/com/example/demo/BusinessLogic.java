package com.example.demo;

import com.example.demo.tables.records.CommRecord;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BusinessLogic {

    private final DSLContext create;

    @Autowired
    public BusinessLogic(DSLContext create) 
    {
        this.create = create;
    }

    public CommRecord doStuff()
    {
        // some code that makes use of the code generated by jOOQ
        CommRecord test = create.selectFrom(Tables.COMM).fetchOne();

        return test;
    }
}
