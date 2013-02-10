package com.jff.grapheditor.types;

public class AbstractElement {

	private final ElementType elementType;

	private long id;

	public AbstractElement(ElementType elementType) {
		super();
		this.elementType = elementType;
	}

	public ElementType getElementType() {
		return elementType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
