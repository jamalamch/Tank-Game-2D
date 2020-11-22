package crach.stage.game.utils;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import crach.stage.game.CrachGame;
import crach.stage.game.entity.Entity;
import crach.stage.game.entity.Crach.Crach;

public class WorldContactListener implements ContactListener {

	
	
	
	@Override
	public void beginContact(Contact contact) {
		
		
        final Fixture fixA ;
        final Fixture fixB ;
        final Entity entityA;
        final Entity entityB;
        fixA = contact.getFixtureA();
        fixB = contact.getFixtureB();
        
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        
        switch (cDef){
        case CrachGame.LAMAS_BIT     | CrachGame.OBJECT_BIT:
        case CrachGame.OBJECT_BIT    | CrachGame.PLACE_BOX:
        case CrachGame.ZONE_BIT      | CrachGame.OBJECT_BIT:
        case CrachGame.CRYSTAL_BIT   | CrachGame.CRACH_BIT :
        case CrachGame.FRENDS_BIT    | CrachGame.CRACH_BIT :
        case CrachGame.ENIMY_BIT     | CrachGame.ENIMY_BIT:
        case CrachGame.DOOR_BIT    	 | CrachGame.LAMAS_BIT:
        case CrachGame.DOOR_BIT    	 | CrachGame.CRACH_BIT:
        case CrachGame.DOOR_BIT    	 | CrachGame.ENIMY_BIT:
        case CrachGame.CRYSTAL_BIT 	 | CrachGame.OBJECT_BIT:
        case CrachGame.CRYSTAL_BIT 	 | CrachGame.ENIMY_BIT:
        case CrachGame.CRACH_BIT   	 | CrachGame.PLACE_BIT :	
        case CrachGame.FIRE_BIT      | CrachGame.FIRE_BIT:	
        case CrachGame.FIRE_BIT 	 | CrachGame.DOOR_BIT:
        case CrachGame.FIRE_BIT      | CrachGame.OBJECT_BIT:
        case CrachGame.FIRE_BIT      | CrachGame.CRACH_BIT:
        case CrachGame.FIRE_BIT      | CrachGame.LAMAS_BIT:
        case CrachGame.FIRE_BIT      | CrachGame.ENIMY_BIT:
        case CrachGame.FIRE_BIT      | CrachGame.FRENDS_BIT:
        case CrachGame.EXPLOSION_BIT | CrachGame.CRACH_BIT:
        case CrachGame.EXPLOSION_BIT | CrachGame.ENIMY_BIT:
        case CrachGame.EXPLOSION_BIT | CrachGame.FRENDS_BIT:
        case CrachGame.EXPLOSION_BIT | CrachGame.OBJECT_BIT:
        case CrachGame.EXPLOSION_BIT | CrachGame.BOMB_BIT:
        case CrachGame.BOMB_BIT		 | CrachGame.ENIMY_BIT:
        case CrachGame.BOMB_BIT		 | CrachGame.CRACH_BIT:
        case CrachGame.BOMB_BIT		 | CrachGame.FIRE_BIT:
        case CrachGame.BOMB_BIT		 | CrachGame.OBJECT_BIT:
        case CrachGame.BOMB_BIT		 | CrachGame.NOTHING_BIT:
        case CrachGame.BOMB_BIT		 | CrachGame.BOMB_BIT:
        case CrachGame.BOMB_BIT		 | CrachGame.DOOR_BIT: 	
        case CrachGame.ZONE_BIT      | CrachGame.CRACH_BIT :
        case CrachGame.FIRE_BIT      | CrachGame.NOTHING_BIT :

           entityA = entityA(fixA);
           entityB = entityB(fixB);
        if (entityA != null)
        		entityA.onContactStart(entityB);
        if (entityB != null) 
            	entityB.onContactStart(entityA);
        break;             
        case CrachGame.LAMAS_BIT | CrachGame.NOTHING_BIT:
            entityA = entityA(fixA);
            entityB = entityB(fixB);
            if( entityA instanceof Crach ) {
                  ((Crach)entityA).actGunPoss(6,10);
            }
            else {
                ((Crach)entityB).actGunPoss(6,10);
            } 
        break;
        }
	}

	@Override
	public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        
        final Entity entityA;
        final Entity entityB;
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
        case CrachGame.OBJECT_BIT | CrachGame.LAMAS_BIT:
        case CrachGame.OBJECT_BIT | CrachGame.PLACE_BOX:
        case CrachGame.ZONE_BIT | CrachGame.CRACH_BIT :

         entityA = entityA(fixA);
         entityB = entityB(fixB);
         
        if (entityA != null)  
        	entityA.onContactEnd(entityB);
    	if(entityB != null) 
            entityB.onContactEnd(entityA);
        
        break;
        }
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

    private static Entity entityA(Fixture fixt) {
        final Object dataA = fixt.getBody().getUserData();
        return dataA instanceof Entity ? (Entity) dataA : null;
    }

    private static Entity entityB(Fixture fixt) {
        final Object dataB = fixt.getBody().getUserData();
        return dataB instanceof Entity ? (Entity) dataB : null;
    }
	
}
