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

import javax.swing.JOptionPane;

import application.Cell;
import javafx.animation.PathTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
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

public class MainController implements Initializable {
	private int tileCount;
	private Image image;

	private double tileSize;
	private List<Cell> cells = new ArrayList<Cell>();
	private Stack<Board> solutions = new Stack<Board>();

	@FXML
	private Pane panel;
    @FXML
    private Label lbResult;

    @FXML
    private ComboBox<String> cbHeuristic;

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

			});

			// position images on scene
			cell.getCurrentImage().relocate(tileSize * cell.getX(), tileSize * cell.getY());
			panel.getChildren().add(imageView);

		}

	}
	
	public int getblank() {
		int res = 0;
		for(int i = 0; i < cells.size(); i++) {
			if(cells.get(i).getIndex() == 0) {
				res = i;
			}
		}
		return res;
	}

	public void shuffle() {


		Random rnd = new Random();

		for (int i = 0; i < 10; i++) {

			int a = rnd.nextInt(cells.size());
			int b = rnd.nextInt(cells.size());

			if (a == b)
				continue;
			
			swap(cells.get(a), cells.get(b));

		}
		
		
//		Random rndRandom = new Random();
//		for(int i = 0; i < 1000; i++) {
//			int a = rndRandom.nextInt(3);
//			int blank = this.getblank();
//			if(this.getblank()/tileCount > 0 && a == 0) {
//				swap(cells.get(blank), cells.get(blank - tileCount));
//			}
//			if(this.getblank()%tileCount < tileCount - 1 && a == 1) {
//				swap(cells.get(blank), cells.get(blank + 1));
//			}
//			if(this.getblank()/tileCount < tileCount - 1 && a == 2) {
//				swap(cells.get(blank), cells.get(blank + tileCount));
//			}
//			if(this.getblank()%tileCount > 0 && a == 3) {
//				swap(cells.get(blank), cells.get(blank - 1));
//			}
//		}
	}
	

	

	public void swap(Cell cellA, Cell cellB) {

		ImageView tmp = cellA.getCurrentImage();
		cellA.setCurrentImage(cellB.getCurrentImage());
		cellB.setCurrentImage(tmp);
		int temp = cellA.getIndex();
		cellA.setIndex(cellB.getIndex());
		cellB.setIndex(temp);

	}

	public void initCombobox() {
		ObservableList<String> list = FXCollections.observableArrayList(new String[] { "Heuristic1", "Heuristic2", "Heuristic3",
				"Heuristic4", "Heuristic5","Heuristic6"});
		cbHeuristic.setItems(list);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initCombobox();
	}
	

	public void moveCell(Node node) {

		Cell currentCell = null;
		for (Cell tmpCell : cells) {
			if (tmpCell.getCurrentImage() == node) {
				currentCell = tmpCell;
				break;
			}
		}
		

		if (currentCell == null)
			return;

		Cell emptyCell = null;

		for (Cell tmpCell : cells) {
			if (tmpCell.isEmpty()) {
				emptyCell = tmpCell;
				break;
			}
		}

		if (emptyCell == null)
			return;


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

        Cell cellA = currentCell;
        Cell cellB = emptyCell;
        pathTransition.setOnFinished(actionEvent -> {

            swap( cellA, cellB);


        });

        pathTransition.play();
        
		for(int j = 0; j < cells.size(); j++) {
			System.out.print(cells.get(j).getIndex() + " ");
		}
		System.out.println();
        



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

	public static class LineToAbs extends LineTo {

		public LineToAbs(Node node, double x, double y) {
			super(x - node.getLayoutX() + node.getLayoutBounds().getWidth() / 2,
					y - node.getLayoutY() + node.getLayoutBounds().getHeight() / 2);
		}

	}
	
	public void solve(ActionEvent event) {
		int[] tiles = new int[cells.size()];
		for (int i = 0; i < cells.size(); i++) {
			Cell cell = cells.get(i);
			System.out.print(cell.getIndex() + " ");
			tiles[i] = cells.get(i).getIndex();
		}
		Board board = new Board(tiles, tileCount);
		
		int x = 1;
		SelectionModel<String> model = cbHeuristic.getSelectionModel();
		String selected = model.getSelectedItem();
		if(selected == null) {
			x = 3;
		}
		if(selected.equals("Heuristic1")) {
			x = 1;
		}
		else if(selected.equals("Heuristic2")) {
			x = 2;
		}
		else if(selected.equals("Heuristic3")) {
			x = 3;
		}
		else if(selected.equals("Heuristic4")) {
			x = 4;
		}
		else if(selected.equals("Heuristic5")) {
			x = 5;
		}
		else if(selected.equals("Heuristic6")) {
			x = 6;
		}
		else {
			x = 1;
		}
		if(board.isSolve()) {
			
			puzzle.AstarSolver astarSolver = new puzzle.AstarSolver(board,x);
			solutions = astarSolver.solution;
			for(Board board22 : astarSolver.solution) {
				System.out.println(board22.toString());
			}
			if(astarSolver.solution.size() > 0)
			lbResult.setText("Tìm thấy lời\n giải trong \n" + astarSolver.timeSolve + "milis\nsố nút đã duyệt " + astarSolver.countNode +"\nsố bước đi "+ astarSolver.solution.size());
			else {
				lbResult.setText("Không tìm được lời giải");
			}
		}
		else {
			lbResult.setText("TRẠNG THÁI KHÔNG HỢP\n LỆ ĐỂ GIẢI");
		}

	}
	
	public void run() {
		if(!solutions.isEmpty()) {
			int index = solutions.pop().getBlank();
			moveCell(cells.get(index).getCurrentImage());
		}
		else {
			System.out.println("Giai xong roi");
		}
	}
	
	public void printBoard(ActionEvent event) {
		for(Cell cell : cells) {
			System.out.print(cell.getIndex() + " ");
		}
	}

}
