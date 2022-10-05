package library;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.Writer;
import java.io.Serializable;
import java.util.Objects;

public  class ScientificLibraryHall implements IHall,Serializable {
    
    class Item {
        public IBook data;
        public Item next;

        public Item(IBook _book, Item _next) {
            data = _book;
            next = _next;
        }

        public Item(IBook _book) {
            data = _book;
        }
    }
    
     Item first = null;
    private int size;
    private String hallName;

   
    @Override
    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

  
    @Override
    public String getHallName() {
        return hallName;
    }
    
   
    @Override
    public void setSize(int size) {
        this.size = size;
        if (size<0){
            throw new InvalidBookCountException("Amount of Books is a negative number!");
        }
    }

    public ScientificLibraryHall(){
    }
    
    
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int getSize() {
        return size;
    }
    
  
    public ScientificLibraryHall (String hallName, int _size) {
        this.hallName=hallName;
        for (int i = 0; i < _size; ++i) {
            addElement(new ScientificBook());//??????
            if (_size<0){
            throw new InvalidBookCountException("Amount of Books is a negative number!");
        }
        }
    }
    
 
    public ScientificLibraryHall(String hallName,IBook[] books) {
        this.hallName=hallName;
        for (IBook book : books) {
            addElement(book);
        }
    }

    public final void addElement(IBook _book) { //добавление элемента
         if (isEmpty()) {
            first = new Item(_book);
            first.next = first;
            ++size;
            return;
        }
        Item last = first;
        do {
            last = last.next;
        } while (last.next != first);
        last.next = new Item(_book, first);
        ++size;
    }
    
    @Override
   public IBook [] getArray(){
       IBook [] tempArr = new IBook [this.getSize()];
       for (int i=0;i<this.getSize();i++){
           tempArr[i]=this.getBook(i);
       }
       return tempArr;
   }
    @Override
    public void replaceBook (int bookNum, IBook newBook){ //заена книги
        if (isEmpty()){
            return;
        } 
        if (bookNum>this.getSize()){
         throw new BookIndexOutOfBoundsException("The size of the array is "+this.getSize()+" ttry another index") ;
        }
        Item target = first;  
          Item prev=null;
          int cnt=0;
          do{
              prev=target;
              target=target.next;
              cnt++;
          }while(cnt!=bookNum);
          Item _new = new Item(newBook);
          prev.next=_new;
          _new.next=target.next;
    }

    @Override
    public void addBook( int indX,IBook _book) { //добавление по номеру в зале
        if (isEmpty()) {
           this.addElement(_book);
        }
        if (indX > size) {
         throw new BookIndexOutOfBoundsException("The size of the array is "+this.getSize()+" ttry another index") ;
        }
        Item place = first;
        int curindx = 0;
        do {
            ++curindx;
            place = place.next;
        } while (curindx != indX);
        Item _new = new Item(_book, place.next);
        place.next = _new;
        ++size;
    }

   
    @Override
    public void deleteBook(int indX) { //удаление книги
        if (isEmpty())
            return;
        if (indX > size)
         throw new BookIndexOutOfBoundsException("The size of the array is "+this.getSize()+" ttry another index") ;
        Item curptr = first;
        int curind = 0;
        do{
            ++curind;
            curptr = curptr.next;
        }while(curind + 1 != indX);
         if(indX==0){
            first=first.next;
        }
        curptr.next = curptr.next.next;
        --size;
    }


    @Override
    public void printNames (){ //печать наваний книг
        if (isEmpty())
         System.out.println("No books found");
System.out.println("Books in " +this.getHallName()+ " Hall" +" :" );
        Item p = first;
        do {
            System.out.println(  p.data.getName());
            p = p.next;
        } while (p != first);
    } 

   
    @Override
    public double sumCost (){ //стоимость всех книг
        int sum=0;
        if (isEmpty())
            return 0;
        Item p = first;  
          int cnt=0;
        do{
            sum+=p.data.getCost();
            p=p.next;
            cnt++;
        }while (p!=first && cnt!= size);
        return sum;
    }


    @Override
    public IBook getBook(int index) { //получение книги по номеру
        if (isEmpty())
            return null;
        if (index >= size)
         throw new BookIndexOutOfBoundsException("The size of the array is "+this.getSize()+" ttry another index") ;
        Item p = first;
        int cnt = 0;
        do {
            ++cnt;
            p = p.next;
        } while (p != first && cnt + 1 != index);
        return p.data;
    }
    
    @Override
    public IBook getBestBook (){
        if (isEmpty())
            return null;
        Item p = first;  
          int cnt=0;
          Item max = p; // первый элемент = макс
        do{
            if (p.next.data.getCost() > max.data.getCost()){//если след больше макс
            max.data=p.next.data;//макс = след
            }
            p=p.next;
            cnt++;
        }while (p!=first && cnt!= size);
        return max.data;
    }

    
    
     public void outputScientificHall (ScientificLibraryHall hall, DataOutputStream out) throws IOException{
         out.writeByte(hall.getSize());
        for(ScientificBook book : (ScientificBook[])hall.getArray()){
           book.outputScientificBook(book, out);
     }
    }
     
     public ScientificLibraryHall inputScientificHall (DataInputStream in) throws IOException {
          int booksCount=in.readInt();
          ScientificLibraryHall hall = new ScientificLibraryHall("",booksCount);
          in.skip(1);
          for(ScientificBook b: (ScientificBook[])hall.getArray()){
          b.inputBook(in);
          }
          return hall;
     }
     
     public void writeScientificHall(ScientificLibraryHall hall, Writer out) throws IOException {
         out.write(hall.getSize()+" ");
         for (ScientificBook book: (ScientificBook[])hall.getArray()){
             book.writeScientificBook(book, out);
         }
          out.write("\n");
     }
     
     public ScientificLibraryHall readScientificHall(Reader in) throws IOException {
        StreamTokenizer tknz = new StreamTokenizer(in);
        int booksCount=tknz.ttype;
        ScientificLibraryHall hall = new ScientificLibraryHall("",booksCount);
        tknz.nextToken();
        for (ScientificBook book : (ScientificBook[])hall.getArray()){
            book.readScientificBook(in);
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
        final ScientificLibraryHall other = (ScientificLibraryHall) obj;
        if (this.size != other.size) {
            return false;
        }
        if (!Objects.equals(this.hallName, other.hallName)) {
            return false;
        }
        if (!Objects.equals(this.first, other.first)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = this.getSize();
        for (int i=0;i<this.getSize();i++){
        hash^=this.getBook(i).hashCode();
    }
        return hash;
    }

    @Override
    public ScientificLibraryHall clone()  {
        try{
      ScientificLibraryHall newHall = (ScientificLibraryHall) super.clone();
      for (int i=0;i<this.getSize();i++){
      newHall.replaceBook(i, this.getArray()[i].clone());
    }
      return newHall; 
        }catch (CloneNotSupportedException ex){
    }
     return null;  
    }
    
    
}
