package my_beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "DealBean")
@SessionScoped
public class DealBean {

    private String pic;
    private String title;
    private String price;
    private String qty;
    private String availQty;
    private String DealDA;
    private int numOfTries = 0;
    private int maxTries = 3;

    String response = "test";


    public void setPic(String pic) {
        this.pic = pic;
    }


    public String getPic() {
        int comma = DealBeanDA.getDeal(this).indexOf(",");
       pic = DealBeanDA.getDeal(this).substring(0, comma);
        return pic;
    }

    public String getAvailQty() {
        return availQty;
    }

    public void setAvailQty(String availQty) {
        this.availQty = availQty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

   


    /**
     * @return the response
     */



    public String getResponse() {
        int comma = DealBeanDA.getDeal(this).indexOf(",");
        return DealBeanDA.getDeal(this).substring(comma +1, DealBeanDA.getDeal(this).length());
    }


    public String persist() {
          return DealBeanDA.getDeal(this);

    }
}
