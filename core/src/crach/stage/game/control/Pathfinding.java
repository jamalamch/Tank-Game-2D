package crach.stage.game.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;

import crach.stage.game.CrachGame;
public  class Pathfinding {

	IndexedAStarPathFinder<TestNode> mPathFinder;
	ShapeRenderer mShapeRenderer;
	TestGraph mGraph;
	/**
	 * This is a Heuristic function that will estimate how close the current node is
	 * to the end.
	 */
	ManhattanDistanceHeuristic mHeuristic;
  
	/**
	 * Our array of nodes. Make sure that this has the same size as mMap.
	 */
	TestNode[][] mNodes;
    int StrlenX;
	
	public int createMaps(TiledMap tiledMap) {
        TiledMapTileLayer tiles = (TiledMapTileLayer)tiledMap.getLayers().get("bourder");
        StrlenX =tiles.getWidth();
        int StrlenY =tiles.getHeight();
        mNodes = new TestNode[StrlenX][StrlenY];
        mGraph = new TestGraph();
        mHeuristic = new ManhattanDistanceHeuristic();

        // Initialize all the nodes that should be present.
        for (int y = 1; y < StrlenY-1; y++) { 
            for (int x = 1; x < StrlenX-1; x++) {
                if (tiles.getCell(x, y) == null) {
                    mNodes[x][y] = new TestNode(x,y);
                 
                    mGraph.addNode(mNodes[x][y]);
                }
//                else if(tiles.getCell(x, y).getTile().getId() == 2) {
//                    mNodes[x][y] = new TestNode(x,y);
//                    mNodes[x][y].setClose();
//                    mGraph.addNode(mNodes[x][y]);                	
//                }               	
            }
        }
        
        for (int x = 1; x < StrlenX-1; x++) {
            for (int y = 1; y < StrlenY-1; y++) {
            	if(mNodes[x][y] != null) {
            	if(mNodes[x+1][y] == null) mNodes[x][y].addCenter();
            	if(mNodes[x-1][y] == null) mNodes[x][y].addCenter();
                if(mNodes[x][y+1] == null) mNodes[x][y].addCenter();
                if(mNodes[x][y-1] == null) mNodes[x][y].addCenter();
            	if(mNodes[x+1][y+1] == null) mNodes[x][y].addCenter();
            	if(mNodes[x+1][y-1] == null) mNodes[x][y].addCenter();
                if(mNodes[x-1][y-1] == null) mNodes[x][y].addCenter();
                if(mNodes[x-1][y+1] == null) mNodes[x][y].addCenter();
            	}
            }
          }
        
        // Add connection to every neighbour of this node. 
        
        for (int x = 1; x < StrlenX-1; x++) {
            for (int y = 1; y < StrlenY-1; y++) {
                if (null != mNodes[x][y]) {
                	boolean below ,above,left,right;
                	below=above=left=right=false;
                    if(addNodeNeighbour(mNodes[x][y], x - 1, y)) {// Node to left  
                    	left =true;
                    }
                    if(addNodeNeighbour(mNodes[x][y], x + 1, y)) {// Node to right
                    	right=true;
                    }
                    if(addNodeNeighbour(mNodes[x][y], x, y - 1)) {// Node below
                    	below =true;
                    }
                    if(addNodeNeighbour(mNodes[x][y], x, y + 1)) {// Node above
                    	above =true ;
                    }
                    if(right && above) if(addNodeNeighbour(mNodes[x][y], x+1, y + 1)) ;// Node above+right
                    if(right && below) if(addNodeNeighbour(mNodes[x][y], x+1, y - 1)) ; //Node below+reight
                    if(left && above) if(addNodeNeighbour(mNodes[x][y], x-1, y + 1)) ; //Node above+left
                    if(left && below) if(addNodeNeighbour(mNodes[x][y], x-1, y - 1)) ; //Node below+left
                }
            }
        }
        
        mPathFinder = new IndexedAStarPathFinder<TestNode>(mGraph, true);
        return StrlenX;
	}
	public void setCellClose(int xCell ,int yCell,boolean close) {
		if(xCell > 0 && yCell > 0)
		if( mNodes[xCell][yCell] != null) mNodes[xCell][yCell].setClose(close);
	}

	private boolean addNodeNeighbour(TestNode aNode, int aX, int aY) {
		// Make sure that we are within our array bounds.
		if (aX >= 0 && aX < mNodes.length && aY >= 0 && aY < mNodes[0].length) {
			return aNode.addNeighbour(mNodes[aX][aY]);
		}
		return false;
	}

	public ArrayGraphFind calculatePath(int Ex, int Ey, int ToX, int ToY) {
		/** This is where the solution will end up. */
		ArrayGraphFind  mPath = new ArrayGraphFind();

		TestNode startNode = mNodes[Ex][Ey]; // Hardcoded for now
		TestNode endNode = mNodes[ToX][ToY]; // Hardcoded for now

		mPath.clear();
    		mPathFinder.searchNodePath(startNode, endNode, mHeuristic, mPath);


		if (mPath.getCount()== 0) {
			System.out.println("-----No path found-----");
		} else {
			System.out.println("-----Found path-----");
		}
		// Loop throw every node in the solution and select it.
		for (TestNode node : mPath) {
			System.out.println(node);
		}
		return mPath;
	}

	public void  generePath(ArrayGraphFind  mPath){
		for (TestNode node : mPath) {
			node.select(false);
		}
		TestNode startNode = mPath.first(); // Hardcoded for now
		TestNode endNode = mPath.peek(); // Hardcoded for now
		mPath.clear();
			mPathFinder.searchNodePath(startNode,endNode, mHeuristic, mPath);

		if (mPath.getCount() == 0) {
			System.out.println("-----No path found-----");
		} else {
			System.out.println("-----Found path-----");
		}
	}
    public void addPath(ArrayGraphFind  mPath ,TestNode node) {
    	node.select(true);
    	mPath.add(node);
    }
	public TestNode[][] getNodes() {
		return mNodes;
	}
    public void drawPath(Batch batch) {
    	if(mShapeRenderer == null)
    		mShapeRenderer = new ShapeRenderer();
		mShapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		mShapeRenderer.begin(ShapeRenderer.ShapeType.Line);

		for (TestNode[] mNodes : mNodes)
			for (TestNode Node : mNodes)
				if (null != Node)
						Node.render(mShapeRenderer);
		mShapeRenderer.end();
    }
	@SuppressWarnings("rawtypes")
	public class TestNode implements IndexedGraph {

		public final static float TILE_SIZE = 64 / CrachGame.PPM;

		/** Index that needs to be unique for every node and starts from 0. */
		public int xIndex;
		public int yIndex;
		/** Whether or not this tile is in the path. */
		private boolean mSelected = false;
		
		private boolean isClose = false;

		public float Center = 1;
		/** X pos of node. */
		public final float mX;
		/** Y pos of node. */
		public final float mY;

		/**
		 * The neighbours of this node. i.e to which node can we travel to from here.
		 */
		Array<Connection<TestNode>> mConnections = new Array<Connection<TestNode>>();

		/** @param aX needs to be unique for every node and starts from 0. */
		public TestNode(int aX, int aY) {
			xIndex = aX;
			yIndex = aY;
			mX = aX*TILE_SIZE;
			mY = aY*TILE_SIZE;
		}

		@Override
		public int getNodeCount() {
			return mConnections.size;
		}

		public boolean addNeighbour(TestNode aNode) {
			if (null != aNode) {
				mConnections.add(new ConnectionMap(this, aNode, aNode.Center));
				return true;
			}
			return false;
		}

		public void select(boolean select) {
			mSelected = select;
		}
        public void setClose() {
        	setClose(true);
        }
        public void setClose(boolean close) {
        	isClose =close;
        }
		public void addCenter() {
			Center += 0.5f;
		}

		/** Render this tile as a white square or red if included in the found path. */
		public void render(ShapeRenderer aShapeRenderer) {
			if(isClose)
				aShapeRenderer.setColor(Color.BLUE);
			else if (mSelected) {
				aShapeRenderer.setColor(Color.RED);
			} else {
				aShapeRenderer.setColor(Color.WHITE);
			}
			float mX = this.mX+0.15f,mY= this.mY+0.15f,TILE_SIZE = TestNode.TILE_SIZE -0.3f;
			aShapeRenderer.line(mX, mY, mX, mY + TILE_SIZE );
			aShapeRenderer.line(mX, mY, mX + TILE_SIZE , mY);
			aShapeRenderer.line(mX, mY + TILE_SIZE , mX + TILE_SIZE ,mY + TILE_SIZE );
			aShapeRenderer.line(mX + TILE_SIZE , mY, mX + TILE_SIZE ,mY + TILE_SIZE );
		}

		public String toString() {
			return String.format("Index:%d x:%f3 y:%f3", getIndex(null), mX, mY);
		}

		@Override
		public Array getConnections(Object fromNode) {
			return mConnections;
		}

		@Override
		public int getIndex(Object node) {
			return xIndex*(StrlenX)  + yIndex;
		}

	}

	@SuppressWarnings("serial")
	public class TestGraph extends ArrayList<TestNode> implements IndexedGraph<TestNode> {
		
		public TestGraph() {
			super();
		}

		public void addNode(TestNode aNodes) {
			super.add(aNodes);
		}

		public TestNode getNode(int aIndex) {
			return super.get(aIndex);
		}

		@Override
		public int getIndex(TestNode node) {
			return super.indexOf(node);
		}

		@Override
		public int getNodeCount() {
			return super.size();
		}

		@Override
		public Array<Connection<TestNode>> getConnections(TestNode fromNode) {
			return fromNode.mConnections;
		}
	}

	public class ManhattanDistanceHeuristic implements Heuristic<TestNode> {

		@Override
		public float estimate(TestNode node, TestNode endNode) {
			return 0;
		}

	}

	public class ConnectionMap implements Connection<TestNode> {
		private TestNode fromNode;
		private TestNode toNode;
		float Cost;

		public ConnectionMap(TestNode fromNode, TestNode toNode, float Cost) {
			this.fromNode = fromNode;
			this.toNode = toNode;
			this.Cost = Cost;
		}

		@Override
		public float getCost() {
			return Cost;
		}

		@Override
		public TestNode getFromNode() {
			return fromNode;
		}

		@Override
		public TestNode getToNode() {
			return toNode;
		}

	}
	public class ArrayGraphFind  implements GraphPath<TestNode> {
         
	    private ArrayList<TestNode> Node ;
	    private boolean hasDoor = false;
	    private TestNode Door;
	    
		public ArrayGraphFind() {
   			   Node =new ArrayList<Pathfinding.TestNode>();
		}
		public TestNode peek() {
			return Node.get(Node.size()-1);
		}
		public TestNode first() {
			return Node.get(0);
		}
		@Override
		public Iterator<TestNode> iterator() {
             return Node.iterator();
		}

		@Override
		public int getCount() {
              return Node.size();
		}

		@Override
		public TestNode get(int index) {
			return Node.get(index);
		}

		public void add(TestNode node) {
			if(node != null) {
		           node.select(true);
		           if(node.isClose) {
		        	   Door = node;
		        	   hasDoor = true;
		        	   System.out.println(node.mX + " Door "+ node.mY);
		           }
			       Node.add(node);
				}			
			}
        public int fastAdd(int x, int y) {
        	TestNode node = mNodes[x][y]; 
			if(node != null) {
				if(Node.contains(node)) {
					int size =Node.size();
					if(node == Door) {
						hasDoor = false;
						Door = null;
					}
					for(int i = size-1;i > Node.indexOf(node);i--) {
						if(Node.get(i) == Door) {
							hasDoor = false;
							Door = null;
						}
						Node.remove(i).select(false);
					}
					return Node.size()-size;
                   }
				else {
					if(Node.size() > 2)
					if(Math.abs(Node.get(Node.size()-2).xIndex - node.xIndex) == 1 
					&& Math.abs(Node.get(Node.size()-2).yIndex - node.yIndex )== 1) {
						Node.remove(Node.size()-1).select(false);
			            node.select(true);
			            Node.add(node);
			            return 0;
					}
		            node.select(true);
		            Node.add(node);
		            return 1;
				}
				}
	         return 0;
			}
        public boolean isDoor(int x, int y) {
        	TestNode node =mNodes[x][y];
        	if(Node.contains(node)) {
        	    return Node.indexOf(node) <= Node.indexOf(Door);
        	}
        	return false;
        }
        public boolean hasDoor() {
        	return hasDoor;
        }

		@Override
		public void clear() {
                Node.clear();			
		}

		@Override
		public void reverse() {
			Collections.reverse(Node);
		}
		
		public ArrayList<TestNode> getNode(){
			return Node;
		}
		
	}
}
