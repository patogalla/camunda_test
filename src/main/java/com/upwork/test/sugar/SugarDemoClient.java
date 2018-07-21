package com.upwork.test.sugar;

import com.sugaronrest.*;
import com.sugaronrest.modules.Contacts;
import com.upwork.test.config.SugarDemoConfig;
import org.camunda.bpm.engine.delegate.DelegateExecution;

import java.util.ArrayList;
import java.util.List;

public class SugarDemoClient {

    public static final String SUGAR_CONTACTS_MODULE = "Contacts";

    private SugarRestClient buildClient() {
        return new SugarRestClient(SugarDemoConfig.Url, SugarDemoConfig.Username, SugarDemoConfig.Password);
    }

    /**
     * READ by phone_work
     *
     * @param phoneWork
     * @return
     */
    public List<Contacts> readContactByPhoneWork(String phoneWork) {
        SugarRestRequest request = new SugarRestRequest(SUGAR_CONTACTS_MODULE, RequestType.BulkRead);
        request.setParameter(phoneWork);
        request.getOptions().setMaxResult(10);
        request.getOptions().setQuery("contacts.phone_work='" + phoneWork + "'");
        request.getOptions().setSelectFields(getSelectedField());
        request.getOptions().getSelectFields().add(NameOf.Contacts.Id);
        return (List<Contacts>) buildClient().execute(request).getData();
    }

    /**
     * CREATE new contact
     *
     * @param contact
     * @return
     */
    public SugarRestResponse createContactByType(Contacts contact) {
        SugarRestRequest request = new SugarRestRequest(RequestType.Create);
        request.setModuleType(Contacts.class);
        request.setParameter(contact);
        request.getOptions().setSelectFields(getSelectedField());
        return buildClient().execute(request);
    }

    /**
     * UPDATE contact
     *
     * @param contact
     * @return
     */
    public SugarRestResponse updateContact(Contacts contact) {
        SugarRestRequest request = new SugarRestRequest(SUGAR_CONTACTS_MODULE, RequestType.Update);
        request.setParameter(contact);
        request.getOptions().setSelectFields(getSelectedField());
        return buildClient().execute(request);
    }

    public Contacts buildContact(DelegateExecution execution) {
        Contacts contacts = new Contacts();
        Object id = execution.getVariable(NameOf.Contacts.Id);
        if (id != null) {
            contacts.setId(id.toString());
        }
        contacts.setPhoneWork(execution.getVariable(NameOf.Contacts.PhoneWork).toString());
        contacts.setFirstName(execution.getVariable(NameOf.Contacts.FirstName).toString());
        contacts.setLastName(execution.getVariable(NameOf.Contacts.LastName).toString());
        contacts.setTitle(execution.getVariable(NameOf.Contacts.Title).toString());
        contacts.setDepartment(execution.getVariable(NameOf.Contacts.Department).toString());
        return contacts;
    }

    public static List<String> getSelectedField() {
        List<String> selectedFields = new ArrayList<String>();
        selectedFields.add(NameOf.Contacts.Id);
        selectedFields.add(NameOf.Contacts.PhoneWork);
        selectedFields.add(NameOf.Contacts.FirstName);
        selectedFields.add(NameOf.Contacts.LastName);
        selectedFields.add(NameOf.Contacts.Title);
        selectedFields.add(NameOf.Contacts.Department);
        return selectedFields;
    }

}
