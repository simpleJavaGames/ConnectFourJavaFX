package UnitTests;

import ConnectFourService.ConnectFourService;
import Exceptions.ColumnFullException;

public class ConnectFourServiceUnitTesting {
    public static void main(String[] args) {
        ConnectFourService service = new ConnectFourService();

        try{service.dropPiece(1);}catch (ColumnFullException e){
        }
        try{service.dropPiece(0);}catch (ColumnFullException e){
        }
        try{service.dropPiece(1);}catch (ColumnFullException e){
        }
        try{service.dropPiece(0);}catch (ColumnFullException e){
        }
        try{service.dropPiece(1);}catch (ColumnFullException e){
        }
        try{service.dropPiece(0);}catch (ColumnFullException e){
        }
        try{service.dropPiece(1);}catch (ColumnFullException e){
        }
        try{service.dropPiece(0);}catch (ColumnFullException e){
        }

        System.out.println(service);


    }
}
