package com.upwork.test.tasks;

import com.sugaronrest.*;
import com.sugaronrest.modules.Contacts;
import com.upwork.test.sugar.SugarDemoClient;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProcessRequestDelegate implements JavaDelegate {

    private final static Logger LOGGER = LoggerFactory.getLogger(ProcessRequestDelegate.class);
    private final static String EXISTS_PARAM = "exists";

    private SugarDemoClient sugarDemoClient = new SugarDemoClient();

    public void execute(DelegateExecution execution) throws Exception {
        Object phoneWork = execution.getVariable(NameOf.Contacts.PhoneWork);
        execution.setVariable(EXISTS_PARAM, false);
        if (phoneWork != null) {
            LOGGER.info("Getting contact by phone : '" + phoneWork + "'...");
            List<Contacts> contactos = sugarDemoClient.readContactByPhoneWork(phoneWork.toString());
            boolean exists = contactos.size() > 0;
            if (exists) {
                execution.setVariable(EXISTS_PARAM, exists);
                Contacts contact = contactos.get(0);
                LOGGER.info("Contact found id: {}", contact.getId());
                execution.setVariable(NameOf.Contacts.Id, contact.getId());
            }
        }
    }

}