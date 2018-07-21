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

public class UpdateTaskDelegate implements JavaDelegate {

    private final static Logger LOGGER = LoggerFactory.getLogger(UpdateTaskDelegate.class);

    private SugarDemoClient sugarDemoClient = new SugarDemoClient();

    public void execute(DelegateExecution execution) throws Exception {
        Object id = execution.getVariable(NameOf.Contacts.Id);
        LOGGER.info("Updating contact by id : '" + id + "'...");
        Contacts contacts = sugarDemoClient.buildContact(execution);
        SugarRestResponse response = sugarDemoClient.updateContact(contacts);
        LOGGER.info("Contact updated by id : {} - {}", response.getStatusCode(), response.getJsonRawResponse());
    }

}