package com.udacity.jwdnd.course1.cloudstorage.spo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmit;

    @FindBy(id = "noteTitle")
    private WebElement noteTitleSubmited;

    @FindBy(id = "noteDescription")
    private WebElement noteDescriptionsubmited;


    @FindBy(id = "credentialUrlAfterSubmit")
    private WebElement credentialUrlSubmitted;

    @FindBy(id = "credentialUsernameAfterSubmit")
    private WebElement credentialUsernameSubmitted;

    @FindBy(id = "credentialPasswordAfterSubmit")
    private WebElement credentialPasswordSubmitted;

    @FindBy(id = "addNewNote")
    private WebElement addNewNoteButton;

    @FindBy(id = "noteSaveSubmit")
    private WebElement noteSaveSubmit;

    @FindBy(id = "editNoteButton")
    private WebElement editNoteButton;

    @FindBy(id = "deleteNoteButton")
    private WebElement deleteNoteButton;

    private WebDriver webDriver;


    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }


    public void saveNote(String noteTitle, String noteDescription) {
        noteTab.click();
        addNewNoteButton.click();
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.sendKeys(noteDescription);
        noteSubmit.click();
    }

    public void logout() {
        this.logoutButton.click();
    }

    public String getNoteTitle() {
        return noteTitleSubmited.getText();
    }

    public String getNoteDescription() {
        return noteDescriptionsubmited.getText();
    }

    public String getCredentialUrlSubmitted() {
        return credentialUrlSubmitted.getText();
    }

    public String getCredentialUsernameSubmitted() {
        return credentialUsernameSubmitted.getText();
    }

    public String getCredentialPasswordSubmitted() {
        return credentialPasswordSubmitted.getText();
    }
}
