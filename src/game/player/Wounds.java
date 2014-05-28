package game.player;

import game.enums.SpaceType;

public class Wounds {
	Hit[] hits;
	int hitIndex;
	
	Wounds() {
		hits = new Hit[20];
		hitIndex = 0;	
	}
	
	public void addHit(SpaceType type) {
		if(hitIndex == hits.length) {
			Hit[] temp = new Hit[hits.length*2];
			for(int i = 0; i < hitIndex; i++) {
				temp[i] = hits[i];
			}
			
			hits = temp;
		}
		
		Hit hit = new Hit(type);
		hits[hitIndex] = hit;
		hitIndex++;
	}
	
	public Hit[] getHits() {
		return hits;
	}
	
	public void setHits(int i, Hit h) {
		hits[i] = h;
	}
	
	public void setHitsIndex(int i) {
		hitIndex = i;
	}
	
	public int getHitsIndex() {
		return hitIndex;
	}
	
	@Override
	public String toString() {
		String output = "Hits:\n";
	
		for(int i = 0; i < hitIndex; i++) {
			output += hits[i] + "\n";
		}
		
		output += "\n";
		
		return output;
	}

	public void bleedOut() {
		float bleedRate = (float) Math.random() * 2;
		//float crackRate = 1;
		
		for(int i = 0; i < hitIndex - 1; i++) {
			if(hits[i].currentRadius < hits[i].getMax()) {
				hits[i].increment();	
			}
		}
		
	}
}
