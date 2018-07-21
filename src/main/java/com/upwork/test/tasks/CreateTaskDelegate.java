package com.upwork.test.tasks;

import com.sugaronrest.*;
import com.sugaronrest.modules.Contacts;
import com.upwork.test.config.SugarDemoConfig;
import com.upwork.test.sugar.SugarDemoClient;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CreateTaskDelegate implements JavaDelegate {

    private final static Logger LOGGER = LoggerFactory.getLogger(CreateTaskDelegate.class);

    private SugarDemoClient sugarDemoClient = new SugarDemoClient();

    public void execute(DelegateExecution execution) throws Exception {
        if (execution.getVariable(NameOf.Contacts.PhoneWork) != null) {
            LOGGER.info("Creating contact by phone : '" + execution.getVariable(NameOf.Contacts.PhoneWork) + "'...");
            Contacts contacts = sugarDemoClient.buildContact(execution);
            SugarRestResponse response = sugarDemoClient.createContactByType(contacts);
            LOGGER.info("Contact created by phone : {} - {}", response.getStatusCode(), response.getJsonRawResponse());
        }
    }


}