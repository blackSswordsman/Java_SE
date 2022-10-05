package library;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.Writer;
import java.util.Objects;

public class ScientificBook  implements IBook, Cloneable {
    protected String author;
    protected String name;
    protected double cost;
    protected int year;
    private float index; 
     
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
    

    public float getIndex(){
        return index;
    }
    

    public void setIndex(float index){
        this.index=index;
    }
    
    ScientificBook (){
        this.author="";
        this.cost=0.0;
        this.index=0;
        this.name="";
        this.year=0;
    }
    ScientificBook ( String author, int year){
         this.author=author;
         this.year=year;
    }
    ScientificBook (String author,String name,double cost, int year,float index){
        this.author=author;
        this.name=name;
        this.year=year;
        this.cost=cost;
        if (cost<0){
            throw new InvalidBookPriceException("Price is a negative number!");
        }
        this.index=index;
}

    
    
     public  void outputScientificBook (ScientificBook book, DataOutputStream out) throws IOException{
        out.writeUTF(book.getAuthor());
        out.writeUTF(book.getName());
        out.writeDouble(book.getCost());
        out.writeByte(book.getYear());
        out.writeFloat(book.getIndex());
      }
      
     public ScientificBook inputBook (DataInputStream in) throws IOException {
       ScientificBook book = new ScientificBook();
        book.setAuthor(in.readUTF());
        book.setName(in.readUTF());
        book.setCost(in.readDouble());
        book.setYear(in.readInt());
        book.setIndex(in.readFloat());
        return book;
    }
     
     public void writeScientificBook(ScientificBook book, Writer out) throws IOException {
         out.write("\"" + book.getAuthor() + "\""+"\t");
         out.write("\"" + book.getName() + "\""+"\t");
         out.write(String.valueOf(book.getCost())+" ");
         out.write(String.valueOf(book.getYear()) + " ");
         out.write(String.valueOf(book.getIndex())+" ");
     }
     
     public ScientificBook readScientificBook(Reader in) throws IOException {
         StreamTokenizer tknz = new StreamTokenizer(in);
      ScientificBook book = new ScientificBook();
      book.setAuthor(tknz.sval);
      tknz.nextToken();
      book.setName(tknz.sval);
      tknz.nextToken();
      book.setCost(tknz.nval);
      tknz.nextToken();
      book.setYear(tknz.ttype);
      tknz.nextToken();
      book.setIndex(tknz.ttype);
      tknz.nextToken();
      return book;
    }
    
     @Override
    public String toString() {
       return new StringBuilder ("Author: ").append(getAuthor()).append(" Name: ")
       .append(getName()).append(" Year: ").append(getYear()).append(" Index: ")
       .append(getIndex()).append(" ").toString();
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
        final ScientificBook other = (ScientificBook) obj;
        if (Double.doubleToLongBits(this.cost) != Double.doubleToLongBits(other.cost)) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        if (Float.floatToIntBits(this.index) != Float.floatToIntBits(other.index)) {
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
             ^(Float.floatToIntBits(this.index)>>16)^(Float.floatToIntBits
                    (this.index));
        
    }

    @Override
    public ScientificBook clone() {
        try{
        return (ScientificBook)super.clone(); 
    }catch(CloneNotSupportedException ex){
    }
    return null;
}
}
