/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.zalotraing.server.cache;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 *
 * @author root
 */
public class CustomLinkedList<E> {
    private Node<E> first = null;
    private Node<E> last = null;
    private int size = 0;
    public CustomLinkedList(){
    }

    public void addFirst(E e) {
	if (first == null){
	    first = new Node<>(null,e,null);
	    last = first;
	}
	else{
	    
	    Node<E> secondNode = first;
	    
	    first = new Node<>(null,e,secondNode);
	    secondNode.prev = first;
	    
	}
	size ++;
    }

    public E removeLast() {
	if (last == null){
	    throw new NoSuchElementException();
	}
	else
	{
	    Node<E> prevNode = last.prev;
	    
	    E element = last.item;
//	    last.item = null;
//	    last.prev = null;
	    last = prevNode;
	    if (last == null)
		first = null;
	    else
		last.next = null;
	    size --;
	    return element;
	}
    }

    public E removeFirst() {
	if (first == null){
	    throw new NoSuchElementException();
	}
	else{
	    Node<E> nextNode = first.next;
	    E element = first.item;
//	    first.next = null;
//	    first.item = null;
	    first = nextNode;
	    if (first == null)
		last = null;
	    else
		first.prev = null;
	    size --;
	    return element;
	}
    }

    public E remove(Node<E> removeNode) {
	//if the remove node is first
	if (removeNode.prev == null){
	    return removeFirst();
	}
	//if the remove node is last
	else if (removeNode.next == null){
	    return removeLast();
	}
	//general cases
	else {
	    Node<E> prevNode = removeNode.prev;
	    Node<E> nextNode = removeNode.next;
	    E element = removeNode.item;
	    prevNode.next = nextNode;
	    nextNode.prev = prevNode;
	    size--;
	    return element;
	}
    }
    
    public void moveToFirst(Node<E> movNode){
	Node<E> prevNode = movNode.prev;
	Node<E> nextNode = movNode.next;
	//already the first node
	if (prevNode == null)
	    return;
	//it is the last node
	else if (nextNode == null){
	    last = prevNode;
	    last.next = null;
	}
	//general cases
	else {
	    prevNode.next = nextNode;
	    nextNode.prev = prevNode;
	}
	movNode.next = first;
	movNode.prev = null;
	first.prev = movNode;
	first = movNode;
    }
    
    public E getElement(Node<E> inputNode){
	return inputNode.item;
    }
    
    public void setElement(Node<E> changeNode, E element){
	changeNode.item = element;
    }
    public Node<E> getFirstNode(){
	return this.first;
    }
}
