/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my_beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "CartBean")
@SessionScoped
public class CartBean {

    private int orderNum = 1000;
    private boolean bought;
    LoginBean lb = (LoginBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LoginBean");

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String goToCart() {
        if (lb.isLogged2().equals("true")) {
            return "Checkout.xhtml";
        }
        return "SignIn.xhtml";
    }

    public String buyItem() {
        if (lb.isLogged2().equals("true")) {
            //Store to database
            bought = true;
            return "Checkout.xhtml";

        }
        return "SignIn.xhtml";
    }

    public String purchase() {
        orderNum += 1;
        bought = false;
        return "processing.xhtml";
    }

    public boolean isBought() {
        if (bought){
            return true;
        }
        return false;
    }
}
