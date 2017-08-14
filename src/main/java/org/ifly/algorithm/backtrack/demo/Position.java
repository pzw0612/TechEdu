/**
 * 
 */
package org.ifly.algorithm.backtrack.demo;


class Position{
    public Position(){
 
    }
 
    public Position(int row, int col){
        this.col = col;
        this.row = row;
    }
 
    public String toString(){
        return "(" + row + " ," + col + ")";
    }
 
    int row;
    int col;
}



 
