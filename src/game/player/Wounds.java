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
		
		Hit hit = new Hit(type);
		hits[hitIndex] = hit;
		hitIndex++;
		System.out.print(this.toString());
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
	
	public String toString() {
		String output = "Hits:\n";
		
		for(int i = 0; i < hitIndex; i++) {
			output += hits[i] + "\n";
		}
		
		output += "\n";
		
		return output;
	}
}
