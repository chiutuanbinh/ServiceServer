/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceprofileserver;

/**
 *
 * @author root
 */
public class Node<E>{
    protected E item;
    protected Node<E> next;
    protected Node<E> prev;
    public Node(Node prev, E item, Node next){
	this.prev = prev;
	this.item = item;
	this.prev = prev;
    }
}
