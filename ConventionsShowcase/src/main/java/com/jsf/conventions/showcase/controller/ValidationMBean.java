/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsf.conventions.showcase.controller;

import com.jsf.conventions.util.MessagesController;
import java.io.Serializable;
import javax.inject.Named;
import javax.validation.constraints.Size;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;

/**
 *
 * @author rmpestano
 */
@Named
@ViewAccessScoped
public class ValidationMBean implements Serializable{
    private Integer number;
    private Integer number2;
    private Integer number3;
    private String input1;
    @Size(min=6,message="minimum size is 6")
    private String input2;
    private String input3;
    @Size(min=6,message="minimum size is 6")
    private String input4;
    private String input5;
    @Size(min=6,message="minimum size is 6")
    private String input6;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber2() {
        return number2;
    }

    public void setNumber2(Integer number2) {
        this.number2 = number2;
    }

    public Integer getNumber3() {
        return number3;
    }

    public void setNumber3(Integer number3) {
        this.number3 = number3;
    }

    public String getInput1() {
        return input1;
    }

    public void setInput1(String input1) {
        this.input1 = input1;
    }

    public String getInput2() {
        return input2;
    }

    public void setInput2(String input2) {
        this.input2 = input2;
    }

    public String getInput3() {
        return input3;
    }

    public void setInput3(String input3) {
        this.input3 = input3;
    }

    public String getInput4() {
        return input4;
    }

    public void setInput4(String input4) {
        this.input4 = input4;
    }

    public String getInput5() {
        return input5;
    }

    public void setInput5(String input5) {
        this.input5 = input5;
    }

    public String getInput6() {
        return input6;
    }

    public void setInput6(String input6) {
        this.input6 = input6;
    }
    
    public void submit(){
        MessagesController.addInfo("All components are valid");
    }
    
}
