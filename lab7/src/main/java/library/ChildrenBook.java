package library;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.Writer;
import java.util.Objects;

public class ChildrenBook  implements IBook, Cloneable {
    protected String author;
    protected String name;
    protected double cost;
    protected int year;
    protected int age;
     
    @Override
    public String getAuthor(){
        return author;
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

    public int getAge (){
        return age;
    }
    
    public void setAge (int age){
        this.age=age;
    }
    
    ChildrenBook(){
        this.author="";
        this.name="";
        this.cost=0;
        this.year=0;
        this.age=0;
    }
    ChildrenBook ( String author, int year){
         this.author=author;
         this.year=year;
    }
    ChildrenBook (String author,String name,double cost, int year,int age){
       this.author=author;
       this.name=name;
       this.cost=cost;
       if (cost<0){
            throw new InvalidBookPriceException("Price is a negative number!");
        }
       this.year=year;
       this.age=age;
}
   
    public  void outputChildrenBook (ChildrenBook book, DataOutputStream out) throws IOException{
                out.writeUTF(book.getAuthor());
                out.writeUTF(book.getName());
                out.writeDouble(book.getCost());
                out.writeByte(book.getYear());
                out.writeByte(book.getAge());
            }
    
    public ChildrenBook inputChildrenBook (DataInputStream in) throws IOException {
        ChildrenBook book = new ChildrenBook();
        book.setAuthor(in.readUTF());
        book.setName(in.readUTF());
        book.setCost(in.readDouble());
        book.setYear(in.readInt());
        book.setAge(in.readInt());
        return book;
    }
    
    public void writeChildrenBook(ChildrenBook book, Writer out) throws IOException {
        out.write( "\"" + book.getAuthor() +"\""+ "\t");
        out.write("\""+ book.getName() + "\""+"\t");
        out.write(String.valueOf(book.getCost())+"\t");
        out.write(String.valueOf(book.getYear()) + "\t");
        out.write(String.valueOf(book.getAge())+ "\t");
    }
    
    public ChildrenBook readChildrenBook(Reader in) throws IOException {
      StreamTokenizer tknz = new StreamTokenizer(in);
      ChildrenBook book = new ChildrenBook();
      book.setAuthor(tknz.sval);
      tknz.nextToken();
      book.setName(tknz.sval);
      tknz.nextToken();
      book.setCost(tknz.nval);
      tknz.nextToken();
      book.setYear(tknz.ttype);
      tknz.nextToken();
      book.setAge(tknz.ttype);
      tknz.nextToken();
      return book;
    }
    
        @Override
    public String toString() {
      return new StringBuilder ("Author: ").append(getAuthor()).append(" Name: ")
       .append(getName()).append(" Year: ").append(getYear()).append(" Age: ")
       .append(getAge()).append(" ").toString();
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
        final ChildrenBook other = (ChildrenBook) obj;
        if (Double.doubleToLongBits(this.cost) != Double.doubleToLongBits(other.cost)) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        if (this.age != other.age) {
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
        return Objects.hashCode(this.name)^Objects.hashCode(this.author)
                ^ Objects.hashCode(this.age);
    }

    @Override
    public ChildrenBook clone()  {
        try{
        return (ChildrenBook) super.clone();
    }catch (CloneNotSupportedException ex){
    }
      return null;
}
}


    