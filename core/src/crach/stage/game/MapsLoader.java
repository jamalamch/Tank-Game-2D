package crach.stage.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.ImageResolver.DirectImageResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader.Element;

public class MapsLoader extends TmxMapLoader implements Disposable{
	ImageResolver imageResolver;
	ObjectMap<String, Texture> textures;
	ObjectMap<String, TiledMap> maps;
	Parameters parameter;
	TiledMapTileSet tileSet;

	
	public MapsLoader() {
		super();
		parameter = new Parameters();
		FileHandle tmxFile = resolve("maps/stage/mapsMain.tmx");
		this.root = xml.parse(tmxFile);
		this.textures = new ObjectMap<String, Texture>();
		this.maps = new ObjectMap<String, TiledMap>();
		
		final Array<FileHandle> textureFiles = getDependencyFileHandles(tmxFile);
		for (FileHandle textureFile : textureFiles) {
			Texture texture = new Texture(textureFile, parameter.generateMipMaps);
			texture.setFilter(parameter.textureMinFilter, parameter.textureMagFilter);
			textures.put(textureFile.path(), texture);
		}
		imageResolver =  new DirectImageResolver(textures);
		TiledMap map = loadTiledMap(tmxFile, parameter, imageResolver);
		tileSet = map.getTileSets().getTileSet(0);
	}
	@Override
	public TiledMap load(String fileName) {
		return this.load(fileName, parameter);
	}
	@Override
	public TiledMap load(String fileName, Parameters parameter) {
		Gdx.app.log("Map","load");
		
		if(maps.containsKey(fileName)) {
			return maps.get(fileName);
		}else {
			FileHandle tmxFile = resolve("maps/stage/"+fileName);
			maps.put(fileName, load(tmxFile,parameter));
			return map;
		}
	}
	public TiledMap load(String path,FileHandle tmxFile) {
		TiledMap tiledMap = this.load(tmxFile,parameter);
		int code = tiledMap.getProperties().get("code",99,Integer.class);
		maps.put(path+(code+1)+".tmx", tiledMap);
		return  tiledMap;
	}
	public TiledMap load(FileHandle tmxFile) {
		TiledMap tiledMap = this.load(tmxFile,parameter);
		return  tiledMap;
	}
	public TiledMap load(FileHandle tmxFile, Parameters parameter) {
		this.root = xml.parse(tmxFile);
		TiledMap map = loadTiledMap(tmxFile, parameter, imageResolver);
		map.setOwnedResources(textures.values().toArray());
		return map;
	}
		@Override
	protected void loadTileSet(Element element, FileHandle tmxFile, ImageResolver imageResolver) {
		if(tileSet != null)
			map.getTileSets().addTileSet(tileSet);
		else
			super.loadTileSet(element, tmxFile, imageResolver);
	}
	
	@Override
	public void dispose() {
		maps.clear();
		for(Texture texture : textures.values())
			texture.dispose();
		
	}

	/***************************************************************************/
	
	protected Array<FileHandle> getDependencyFileHandles (FileHandle tmxFile) {
		Array<FileHandle> fileHandles = new Array<FileHandle>();

		// TileSet descriptors
		for (Element tileset : root.getChildrenByName("tileset")) {
			String source = tileset.getAttribute("source", null);
			if (source != null) {
				FileHandle tsxFile = getRelativeFileHandle(tmxFile, source);
				tileset = xml.parse(tsxFile);
				Element imageElement = tileset.getChildByName("image");
				if (imageElement != null) {
					String imageSource = tileset.getChildByName("image").getAttribute("source");
					FileHandle image = getRelativeFileHandle(tsxFile, imageSource);
					fileHandles.add(image);
				} else {
					for (Element tile : tileset.getChildrenByName("tile")) {
						String imageSource = tile.getChildByName("image").getAttribute("source");
						FileHandle image = getRelativeFileHandle(tsxFile, imageSource);
						fileHandles.add(image);
					}
				}
			} else {
				Element imageElement = tileset.getChildByName("image");
				if (imageElement != null) {
					String imageSource = tileset.getChildByName("image").getAttribute("source");
					FileHandle image = getRelativeFileHandle(tmxFile, imageSource);
					fileHandles.add(image);
				} else {
					for (Element tile : tileset.getChildrenByName("tile")) {
						String imageSource = tile.getChildByName("image").getAttribute("source");
						FileHandle image = getRelativeFileHandle(tmxFile, imageSource);
						fileHandles.add(image);
					}
				}
			}
		}

		// ImageLayer descriptors
		for (Element imageLayer : root.getChildrenByName("imagelayer")) {
			Element image = imageLayer.getChildByName("image");
			String source = image.getAttribute("source", null);

			if (source != null) {
				FileHandle handle = getRelativeFileHandle(tmxFile, source);
				fileHandles.add(handle);
			}
		}

		return fileHandles;
	}
}
