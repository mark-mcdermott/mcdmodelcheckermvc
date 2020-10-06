package view;

import view.renderPages.MainPage;

public class View {

    private MainPage mainPage;

    public View() {
        this.mainPage = new MainPage();
    }



    // getters & setters

    public MainPage getMainPage() {
        return this.mainPage;
    }

    public void setMainPage(MainPage mainPage) {
        this.mainPage = mainPage;
    }
}
