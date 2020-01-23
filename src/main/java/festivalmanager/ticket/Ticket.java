package festivalmanager.ticket;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import festivalmanager.festival.Festival;

@Entity
public class Ticket{

    private @Id @GeneratedValue Long id;
    private String name;
    private javax.money.MonetaryAmount price;
    protected Sort sort;
    private boolean used = false;
    @OneToOne(cascade = {CascadeType.ALL}) private Festival festival;

    @SuppressWarnings("unused")
    public Ticket(){}

	/**
	 * The Ticket represents the general form of a ticket, setting up everything to be used as day or camping ticket
	 * @param name is an unique number, that will also be printed on the ticket
     * @param price is the price per ticket
     * @param festival is the festival the ticket gives you access t
     * @param sort is either Sort.CAMPINGTICKET or Sort.DAYTICKET and defines the duration you are granted access to the festival
	 */

    public Ticket(String name, javax.money.MonetaryAmount price, Festival festival){
        this.name = name;
        this.price = price;
        this.festival = festival;
    }

    public String getName(){
        return this.name;
    }

    public javax.money.MonetaryAmount getPrice(){
        return this.price;
    }

    public Sort getSort(){
        return this.sort;
    }

    public Long getId(){
        return this.id;
    }

    public boolean getUsed(){
        return this.used;
    }

    public Festival getFestival(){
        return this.festival;
    }

    public void setUsed(boolean used){
        this.used = used;
    }
}