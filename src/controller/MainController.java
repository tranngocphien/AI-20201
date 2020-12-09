package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.Inet4Address;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Stack;

import application.Cell;
//import application.Main.LineToAbs;
//import application.Main.MoveToAbs;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import puzzle.Board;
import puzzle_test.AstarSolver;
import puzzle_test.Board2;
import sun.jvm.hotspot.oops.java_lang_Class;

public class MainController implements Initializable {
	private int tileCount;
	private Image image;

	private double tileSize;
	private List<Cell> cells = new ArrayList<Cell>();

	@FXML
	private Pane panel;

	public void setPane(int tileCount) {
		this.tileCount = tileCount;
		if (tileCount == 3) {
			tileSize = 200;
		} else if (tileCount == 4) {
			tileSize = 150;
		} else if (tileCount == 5) {
			tileSize = 120;
		} else
			tileSize = 100;

		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(
					"C:\\Users\\PhienTran\\eclipse-workspace\\Puzzle\\src\\application\\anh.png");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.image = new Image(fileInputStream);

		for (int y = 0; y < tileCount; y++) {
			for (int x = 0; x < tileCount; x++) {
				ImageView tileImageView = new ImageView(image);
				Rectangle2D rectangle2d = new Rectangle2D(x * tileSize, y * tileSize, tileSize, tileSize);
				tileImageView.setViewport(rectangle2d);

				if (x == tileCount-1 && y == tileCount-1) {
					tileImageView = null;
				}

				cells.add(new Cell(x, y, tileCount, tileImageView, tileImageView));

			}
		}
		//shuffle();
		int[] tiles = new int[cells.size()];
		for (int i = 0; i < cells.size(); i++) {
			Cell cell = cells.get(i);
			System.out.print(cell.getIndex() + " ");
			tiles[i] = cells.get(i).getIndex();
		}

		for (int i = 0; i < cells.size(); i++) {

			Cell cell = cells.get(i);

			Node imageView = cell.getCurrentImage();
			if (imageView == null)
				continue;

			imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
				moveCell((Node) mouseEvent.getSource());
				for(int j = 0; j < cells.size(); j++) {
					System.out.print(cells.get(j).getIndex() + " ");
				}
				System.out.println();

			});

			// position images on scene
			cell.getCurrentImage().relocate(tileSize * cell.getX(), tileSize * cell.getY());
			panel.getChildren().add(imageView);

		}

	}

	public void shuffle() {

		Random rnd = new Random();

		for (int i = 0; i < 1000; i++) {

			int a = rnd.nextInt(cells.size());
			int b = rnd.nextInt(cells.size());

			if (a == b)
				continue;

			// skip bottom right cell swap, we want the empty cell to remain there
			// if( cells.get(a)() || cells.get(b).isEmpty())
			// continue;

			swap(cells.get(a), cells.get(b));

		}
	}
	


	public void swap(Cell cellA, Cell cellB) {

		ImageView tmp = cellA.getCurrentImage();
		cellA.setCurrentImage(cellB.getCurrentImage());
		cellB.setCurrentImage(tmp);
		int temp = cellA.getIndex();
		cellA.setIndex(cellB.getIndex());
		cellB.setIndex(temp);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
	

	public void moveCell(Node node) {

		// get current cell using the selected node (imageview)
		Cell currentCell = null;
		for (Cell tmpCell : cells) {
			if (tmpCell.getCurrentImage() == node) {
				currentCell = tmpCell;
				break;
			}
		}

		if (currentCell == null)
			return;

		// get empty cell
		Cell emptyCell = null;

		for (Cell tmpCell : cells) {
			if (tmpCell.isEmpty()) {
				emptyCell = tmpCell;
				break;
			}
		}

		if (emptyCell == null)
			return;

		// check if cells are swappable: neighbor distance either x or y must be 1 for a
		// valid move
		int steps = Math.abs(currentCell.getX() - emptyCell.getX()) + Math.abs(currentCell.getY() - emptyCell.getY());
		if (steps != 1) {
			return;
		} 
		Path path = new Path();
        path.getElements().add(new MoveToAbs(currentCell.getImageView(), (currentCell.getX()*tileSize), (currentCell.getY())*tileSize));
        path.getElements().add(new LineToAbs(currentCell.getImageView(), (emptyCell.getX()*tileSize), (emptyCell.getY()*tileSize)));

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(100));
        pathTransition.setNode(currentCell.getImageView());
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);

        final Cell cellA = currentCell;
        final Cell cellB = emptyCell;
        pathTransition.setOnFinished(actionEvent -> {

            swap( cellA, cellB);


        });

        pathTransition.play();


	}

	public static class MoveToAbs extends MoveTo {

		public MoveToAbs(Node node) {
			super(node.getLayoutBounds().getWidth() / 2, node.getLayoutBounds().getHeight() / 2);
		}

		public MoveToAbs(Node node, double x, double y) {
			super(x - node.getLayoutX() + node.getLayoutBounds().getWidth() / 2,
					y - node.getLayoutY() + node.getLayoutBounds().getHeight() / 2);
		}

	}

	// absolute (layoutX/Y) transitions using the pathtransition for LineTo
	public static class LineToAbs extends LineTo {

		public LineToAbs(Node node, double x, double y) {
			super(x - node.getLayoutX() + node.getLayoutBounds().getWidth() / 2,
					y - node.getLayoutY() + node.getLayoutBounds().getHeight() / 2);
		}

	}
	
	public void change(ActionEvent event) {
		int[] tiles = new int[cells.size()];
		for (int i = 0; i < cells.size(); i++) {
			Cell cell = cells.get(i);
			System.out.print(cell.getIndex() + " ");
			tiles[i] = cells.get(i).getIndex();
		}
		Board board = new Board(tiles, tileCount);
		puzzle.AstarSolver astarSolver = new puzzle.AstarSolver(board);
		for(Board board22 : astarSolver.solution) {
        	System.out.println(board22.toString());
        }
		while(!astarSolver.solution.isEmpty()) {
			int index = astarSolver.solution.pop().getBlank();
			System.out.println(index);
			
		}

	}
	

	


}
