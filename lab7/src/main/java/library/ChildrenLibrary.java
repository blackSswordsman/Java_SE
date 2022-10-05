package library;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.Writer;
import java.io.Serializable;
import java.util.Arrays;

public class ChildrenLibrary implements ILibrary,Serializable {
    protected int hallCount;
    protected IHall [] halls;    
    
//    public int getHallCount() {
//        return hallCount;
//    }
    //конструктор кол-во холлов и массив кол-ва книг по холлам
    public ChildrenLibrary (int hallCount,int [] b) {
        this.hallCount=hallCount;
        halls= new IHall[hallCount];
        for (int i=0;i<hallCount;i++){
            halls[i].setSize(b[i]);
        }
    }
    
    public ChildrenLibrary (IHall [] halls){ //констр массив холлов
        this.hallCount=hallCount;
         this.halls=halls;
     }
    
    @Override
     public int hallsCount (){//сколько холлов
         return halls.length;
     }
    @Override
     public int booksCount (int i){//сколько книг в опред. хоолле
         return halls[i].getSize();
     }
    @Override
     public int sumAllBooks (){//сколько книг в библиотеке
         int sum=0;
         for (int i=0;i<halls.length;i++){
             for (int j=0;j<halls[i].getSize();j++){
                 sum++;
             }
         }
          return sum;   
     }
     
    @Override
     public IHall [] hallsArr(){
         return halls; 
     }
     
    @Override
     public IHall getHall (int index){
         if (index>this.hallsCount()){
             throw new HallIndexOutOfBoundsException("Librar only has "+this.hallsCount()+" halls. Try again");
         }
         return halls[index];
     }
     
    @Override
     public IBook getBook (int indexB){// ????? exception 
         int k=0;
         for (IHall h: halls){
            if (indexB<k+h.getSize()){
                return h.getBook(indexB-k);
            }
            else {
                k+=h.getSize();
            }
         }
         return null;
     }
     
    @Override
     public double sumCost(){
         double sum=0;
         for (int i=0;i<halls.length;i++){
             for (int j=0;j<halls[i].getSize();i++){
                sum+= halls[i].getBook(j).getCost();
             }
         }
         return sum;
     }
     
    @Override
     public IBook [] srtBooks (){
        int sum=sumAllBooks();
        IBook [] srtArr = new IBook [sum];
        int cnt =0;
         for (int i=0;i<halls.length;i++){
             for (int j=0;j<halls[i].getSize();j++){
                     srtArr[cnt++]=halls[i].getBook(j);
             }
         }
         
         for(int i=0;i<srtArr.length-1;i++){
         for (int k=0;k<srtArr.length-i-1;k++){
             IBook tmp = srtArr[k];
             double costTmp = srtArr[k].getCost();
             double costPlus = srtArr[k+1].getCost();
             if (costTmp>costPlus){
                 srtArr[k]=srtArr[k+1];
                 srtArr[k+1]=tmp;
                 
             }
         }
         
         }
         return srtArr;
     }
    @Override
    public void printHalls (){
        for (IHall h: halls){
            System.out.println(h.getHallName()+ " "+ h.getSize());
        }
    }
      
    @Override
      public void replaceHall (int index, IHall hallNew){
          if (index>this.hallsCount()){
              throw new HallIndexOutOfBoundsException("Librar only has "+this.hallsCount()+" halls. Try again");
          }
          halls[index]=hallNew;
      }
      
    @Override
      public void replaceBook (int index, IBook bookRep ){//exception????
          int count=0;
         for (int i=0;i<halls.length;i++){
            if (index<count+halls[i].getSize()){
                 halls[i].replaceBook(count-index,bookRep);
            }
            else {
                count+=halls[i].getSize();
            }
             if (count>this.hallsCount()){
          throw new HallIndexOutOfBoundsException("Librar only has "+this.hallsCount()+" halls. Try again");
                    }
         }
}
      
    @Override
    public void addBookToLib (int index, IBook bookNew){//????
    int count=0;
        for (int i=0;i<halls.length;i++){
               if (index<count+halls[i].getSize()){
                halls[i].addBook(count-index, bookNew);
               } else{
                   count+=halls[i].getSize();
               }
               if (count>this.hallsCount()){
          throw new HallIndexOutOfBoundsException("Librar only has "+this.hallsCount()+" halls. Try again");
                    }
        }
    }
      
    @Override
    public void deleteFromLib (int index){
    int count=0;
        for (int i=0;i<halls.length;i++){
            if (index<count+halls[i].getSize()){
                halls[i].deleteBook(count-index);
            } else{
                   count+=halls[i].getSize();   
                }
        }
        if (count>this.hallsCount()){
          throw new HallIndexOutOfBoundsException("Librar only has "+this.hallsCount()+" halls. Try again");
                    }
    }
    
    @Override
    public IBook getBestBook (){
       double max=halls[0].getBestBook().getCost();
       int maxIndex=0;
       for (int i=0;i<halls.length;i++){
           if(max<=halls[i].getBestBook().getCost()){
               max=halls[i].getBestBook().getCost();
               maxIndex=i;
           }
       }
       return halls[maxIndex].getBestBook() ;
    }
    
      
     public  void outputChildrenLibrary (ChildrenLibrary lib, DataOutputStream out) throws IOException{
         out.writeInt(lib.hallsCount());
        for(ChildrenLibraryHall hall: (ChildrenLibraryHall[])lib.hallsArr()){
            hall.outputChildrenHall(hall, out);
        }
     }
     
      public ChildrenLibrary inputChildrenLibrary (DataInputStream in) throws IOException {
          int halls=in.readInt();
          int temp []=new int[halls];
          ChildrenLibrary lib = new ChildrenLibrary(halls,temp);
          for (ChildrenLibraryHall hall: (ChildrenLibraryHall[])lib.hallsArr()){
           hall.inputChildrenHall(in); 
          
       }
          return lib;
         
      }
      
      public  void writeChildrenLibrary(ChildrenLibrary lib, Writer out) throws IOException {
          out.write(lib.hallsCount()+"\n");
          for (ChildrenLibraryHall hall: (ChildrenLibraryHall[])lib.hallsArr()){
              hall.writeChildrenHall(hall, out);
          }
          
      }
    
     public ChildrenLibrary readChildrenLibrary(Reader in) throws IOException {
        StreamTokenizer tknz = new StreamTokenizer(in);
        int hallsCount=tknz.ttype;
        int temp[]=new int[hallsCount];
        ChildrenLibrary lib = new ChildrenLibrary(hallsCount,temp);
        for (ChildrenLibraryHall hall : (ChildrenLibraryHall[])lib.hallsArr()){
            hall.readChildrenHall(in);
        }
        return lib;
    }
     
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Children Library has ").append(hallsCount()).
                append(" halls\n").toString();
         for (int i=0;i<hallsCount();i++){
             str.append(this.getHall(i).toString());
         }  
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
        final ChildrenLibrary other = (ChildrenLibrary) obj;
        if (this.hallCount != other.hallCount) {
            return false;
        }
        return Arrays.deepEquals(this.halls, other.halls);
    }
    
     @Override
    public int hashCode() {
        int hash = this.hallsCount();
        for (int i =0;i<this.hallsCount();i++){
       hash^=this.getHall(i).hashCode();
        }
        return hash;
    }

    @Override
    public ChildrenLibrary clone(){
        try{
          ChildrenLibrary newLib = (ChildrenLibrary) super.clone();
          for (int i=0;i<this.hallsCount();i++){
              newLib.halls[i]=halls[i].clone();
          }
          return newLib;
        }catch (CloneNotSupportedException ex){
        }
        return null;
    }
    
    
}
     

