package application;

import javafx.scene.image.ImageView;

public class Cell {
	private int x;
	private int y;
	private int index;
	private int tileCount;
	
	private ImageView correctImage;
	private ImageView currentImage;
	
	public Cell(int x, int y,int tileCount, ImageView correctImage, ImageView currentImage) {
		super();
		this.x = x;
		this.y = y;
		int value = y*tileCount + x + 1;
		if(value < tileCount*tileCount) {
			this.index = value;
		}
		else {
			this.index = 0;
		}
		this.correctImage = correctImage;
		this.currentImage = currentImage;
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

	public ImageView getCorrectImage() {
		return correctImage;
	}

	public void setCorrectImage(ImageView correctImage) {
		this.correctImage = correctImage;
	}

	public ImageView getCurrentImage() {
		return currentImage;
	}

	public void setCurrentImage(ImageView currentImage) {
		this.currentImage = currentImage;
	}
	
	public boolean isEmpty() {
		return currentImage == null;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public ImageView getImageView() {
        return currentImage;
    }
	
	

}
