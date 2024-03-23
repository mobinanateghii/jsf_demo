package com.example.demo;

import lombok.Data;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.facelets.FaceletContext;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@Named(value = "sampleBean")
@ManagedBean
@Data
public class SampleBean {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String postalCode;
    private String customCode;
    private String email;
    private String country;
    private String samplePage;
    private String nativeLanguage;
    private List<String> favoriteHobbies; /**since user can select multiple hubby*/
    private final List<String> countryOptions = new ArrayList<>();
    private final List<String> nativeLanguageOptions = new ArrayList<>();
    private final List<String> favoriteHobbiesOptions = new ArrayList<>();

    public SampleBean() {
        /**Pre populate data form => because on first step(in xhtml forms) we call getterMethod! getFirstName()**/
        firstName = "mobina";

        countryOptions.add("Iran");
        countryOptions.add("Dubai");
        countryOptions.add("London");

        nativeLanguageOptions.add("English");
        nativeLanguageOptions.add("Persian");
        nativeLanguageOptions.add("Arabic");

        favoriteHobbiesOptions.add("Sport");
        favoriteHobbiesOptions.add("Reading Book");
        favoriteHobbiesOptions.add("Painting");
        favoriteHobbiesOptions.add("Coding");
    }


    /**NOTE:: when we call countryOptions on xhtml, method whit get prefix will be call
     * sampleBean.countryOptions -> getCountryOptions **/
    public List<String> getCountryOptions() {
        return countryOptions;
    }

    public List<String> getNativeLanguageOptions() {
        return nativeLanguageOptions;
    }

    public List<String> getFavoriteHobbiesOptions() {
        return favoriteHobbiesOptions;
    }

    public void validateCustomCode(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(Objects.isNull(value)){
            throw new ValidatorException(new FacesMessage("code is required!"));
        }

        if(!value.toString().startsWith("CV_")){
            throw new ValidatorException(new FacesMessage("code should starts whit CV_!"));
        }
    }

    public String conditionalNavigation(){
        if(samplePage.equals("pure-jsf")){
            return "sample";
        }else if(samplePage.equals("prime-jsf")){
            return "sample-prime";
        }else {
            return "index";
        }
    }

}
