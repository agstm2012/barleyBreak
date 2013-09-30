package com.badlogic.androidgames.barleybreak;

public class Square implements Cloneable{
	private int x;
	private int y;
	private int value;
    boolean skin = false;
	
	public Square(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

    public boolean isSkin() {
        return skin;
    }

    public void setSkin(boolean skin) {
        this.skin = skin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Square square = (Square) o;

        if (value != square.value) return false;
        if (x != square.x) return false;
        if (y != square.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + value;
        return result;
    }

    public Square clone() throws CloneNotSupportedException {
        return (Square)super.clone();
    }
}
