
package library;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.Writer;
import java.util.Arrays;
import java.io.Serializable;
import java.util.Objects;

public  class ChildrenLibraryHall implements IHall,Cloneable {
    
    protected String hallName; //имя холла
    protected int count; //кол-во книг в холле
    IBook [] books;//массив книг

    @Override
    public String getHallName (){
        return hallName;
    }
    @Override
    public void setHallName (String hallName){
        this.hallName=hallName;
    }
    
    @Override
    public void setSize (int count){
        this.count=count;
        if (count<0){
            throw new InvalidBookCountException("Amount of Books is a negative number!");
        }
    }

    ChildrenLibraryHall (String hallName, int count){ // кнстр имя и кол-во книг
    
      this.hallName=hallName;
      this.count=count;
      if (count<0){
            throw new InvalidBookCountException("Amount of Books is a negative number!");
        }
      books = new IBook[count];

}
    ChildrenLibraryHall (String hallName, IBook [] books){//кнстр имя и массив
     this.hallName=hallName;
     this.count=count;//???
     this.books=books;
}
    @Override
    public int getSize (){//сколько книг в холле
        return books.length;
    } 
    @Override
    public void printNames (){
        for (IBook b: books ){
            System.out.println(b.getAuthor());
        }
    }
    
    @Override
    public IBook[] getArray(){
    return this.books;
}
    @Override
    public double sumCost (){
        double sum=0;
        for (IBook b: books ){
           sum+=b.getCost() ;
        }
        return sum;
    }
    @Override
    public IBook getBook (int index){//получение книги по номеру
        if (index>books.length){
         throw new BookIndexOutOfBoundsException("The size of the array is "+this.getSize()+" ttry another index") ;
        } else
            return books[index];
    }
    @Override
    public void replaceBook (int index,IBook replacedBook){//изменение по номеру 
        if (index>books.length){
         throw new BookIndexOutOfBoundsException("The size of the array is "+this.getSize()+" ttry another index") ; 
        }
        books[index]=replacedBook;
    }
    
    @Override
    public void addBook (int index,IBook book){
        IBook temp [] =new IBook[count+1];
        System.arraycopy(books, 0, temp, 0,index );
        if (index>books.length){
         throw new BookIndexOutOfBoundsException("The size of the array is "+this.getSize()+" ttry another index") ;
        }temp[index]=book;
        System.arraycopy(books, index, temp, index+1, count-index); // count++
        
    }
    @Override
    public void deleteBook (int index){//удаление по номеру
        if (index>books.length){
         throw new BookIndexOutOfBoundsException("The size of the array is "+this.getSize()+" ttry another index") ;
        }
        ChildrenBook [] result = new ChildrenBook [books.length-1];
        System.arraycopy(books, 0, result, 0, index);
        System.arraycopy(books, index+1, result, index, count-index-1);
        
    }
 
    @Override
  public IBook getBestBook(){
       double max=0;
       int maxIndex=0;
       for (int i=0;i<books.length;i++){
           if(max<=books[i].getCost()){
               max=books[i].getCost();
               maxIndex=i;
           }
       }
       return books[maxIndex];
    }
   
    
     public void outputChildrenHall (ChildrenLibraryHall hall, DataOutputStream out) throws IOException{
         out.writeByte(hall.getSize());
        for(ChildrenBook book : (ChildrenBook[])hall.getArray()){
           book.outputChildrenBook(book, out);
     }
    }
     
     public ChildrenLibraryHall inputChildrenHall (DataInputStream in) throws IOException {
          in.skipBytes(5);
         int booksCount=in.readInt();
          ChildrenLibraryHall hall = new ChildrenLibraryHall("",booksCount);
          in.skip(1);
          for(ChildrenBook b: (ChildrenBook[])books){
          //for (int i =0;i<booksCount;i++){
          b.inputChildrenBook(in);
          }
          return hall;
     }
     
     public  void writeChildrenHall(ChildrenLibraryHall hall, Writer out) throws IOException {
         out.write(hall.getSize()+"  ");
         for (ChildrenBook book: (ChildrenBook[])hall.getArray()){
             book.writeChildrenBook(book, out);
         }
         out.write("\n");
     }
     
     public ChildrenLibraryHall readChildrenHall(Reader in) throws IOException {
        StreamTokenizer tknz = new StreamTokenizer(in);
        int booksCount=tknz.ttype;
        ChildrenLibraryHall hall = new ChildrenLibraryHall("",booksCount);
        tknz.nextToken();
        for (ChildrenBook book : (ChildrenBook[])books){
            book.readChildrenBook(in);
        }
        return hall;
    }
      
 @Override
    public String toString() {
       StringBuilder str = new StringBuilder();
       str.append("Children Hall has ").append(getSize()).append(" book(s)\n")
               .toString();
       for (int i=0;i<getSize();i++){
           str.append(this.getBook(i).toString());
       }
       str.append("\n");
       return str.toString();
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
        final ChildrenLibraryHall other = (ChildrenLibraryHall) obj;
        if (this.count != other.count) {
            return false;
        }
        if (!Objects.equals(this.hallName, other.hallName)) {
            return false;
        }
        return Arrays.deepEquals(this.books, other.books);
    }
    
    @Override
    public int hashCode() {
        int hash=this.getSize();
        for (int i=0;i<this.getSize();i++){
            hash^=this.getBook(i).hashCode();
        }
        return hash;
    }

    @Override
    public ChildrenLibraryHall clone()  {
        try{
      ChildrenLibraryHall newHall = (ChildrenLibraryHall) super.clone();
         for (int i=0;i<newHall.getSize();i++){
           newHall.books[i]=(ChildrenBook) books[i].clone();
      }
     
      return newHall; 
        }catch (CloneNotSupportedException ex){
    }
        return null;
}
    
}