package library;

import java.util.Objects;


public class Book implements IBook,Cloneable {
    protected String author;
    protected String name;
    protected double cost;
    protected int year;
    
    public Book (){ //по умолчанию
        this.author="";
        this.name="";
        this.cost=0.0;
        this.year=0;
    }
    public Book (String author,String name,double cost,int year){
        this.author=author;
        this.name=name;
        this.cost=cost;
        if (cost<0){
            throw new InvalidBookPriceException("Price is a negative number!");
        }
        this.year=year;
       
    }
    public Book (String author, int year){
        this.author=author;
        this.year=year;
    }

    
    @Override
    public String getAuthor(){
        return name;
    } 

    @Override
    public void setAuthor (String author){
        this.author=author;
    }

    @Override
    public String getName(){
        return name;
    } 
    @Override
    public void setName (String name){
        this.name=name;
    }
    @Override
    public double getCost(){
        return cost;
    } 
    @Override
    public void setCost (double cost){
        this.cost=cost;
        if (cost<0){
            throw new InvalidBookPriceException("Price is a negative number!");
        }
    }
    @Override
    public int getYear(){
        return year;
    } 
    @Override
    public void setYear (int year){
        this.year=year;
    }

    @Override
    public String toString() {
      return new StringBuilder ("Author: ").append(getAuthor()).append("Name: ")
              .append(getName()).append("Year: ").append(getYear()).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        if (Double.doubleToLongBits(this.cost) != Double.doubleToLongBits(other.cost)) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        if (!Objects.equals(this.author, other.author)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.name)^Objects.hashCode(this.author);
    }


    @Override
    public Book clone()  {
        try{
        return (Book)super.clone();
    }catch (CloneNotSupportedException ex){
    }
        return null;
   }
}
    

    
    
    

