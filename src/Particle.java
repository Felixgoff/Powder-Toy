/**
 *	@Copyright 2015 Chris Beimers/firefreak11
 *
 *	This file is part of BeanPowder.
 *
 *	BeanPowder is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	BeanPowder is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with BeanPowder.  If not, see <http://www.gnu.org/licenses/>.
 **/

package net.codarch.bpowder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.codarch.bpowder.elements.Element;

/**
 * A Specific Particle On Screen
 */

public class Particle {

	public static List<Particle> particles = new ArrayList<Particle>();

	public Element element;
	private Random rand;

	public int x;
	public int y;
	public int dx;
	public int dy;

	public Particle(int x, int y, Element element) {
		this.x = x;
		this.y = y;
		this.element = element;
		rand = new Random();
		switch (element.state) {
		case SOLID:
			dx = dy = 0;
			break;
		case LIQUID:
			dx = rand.nextInt(3) - 1;
			dy = 1;
			break;
		case GAS:
			dx = rand.nextInt(3) - 1;
			dy = rand.nextInt(3) - 1;
			break;
		case POWDER:
			dx = 0;
			dy = 1;
			break;
		}
		particles.add(this);
	}

	public void update() {
		switch (element.state) {
		case GAS:
			if (particleAt(x + dx, y + dy) == null) {
				x += dx;
				y += dy;
				if (x < 0)
					x = 0;
				if (x >= Main.powder.width)
					x = Main.powder.width - 1;
				if (y < 0)
					y = 0;
				if (y >= Main.powder.height)
					y = Main.powder.height - 1;
				dx *= -rand.nextInt(2);
				dy *= -rand.nextInt(2);
			}
			break;
		case LIQUID:
			if (particleAt(x, y + 1) == null)
				y += dy;
			if (particleAt(x + dx, y) == null) {
				x += dx;
				if (x < 0)
					x = 0;
				if (x >= Main.powder.width)
					x = Main.powder.width - 1;
				dx *= -rand.nextInt(2);
			}
			break;
		case POWDER:
			break;
		case SOLID:
			break;
		}
	}

	public static Particle particleAt(int x, int y) {
		for (Iterator<Particle> iterator = particles.iterator(); iterator
				.hasNext();) {
			Particle particle = iterator.next();
			if (particle.x == x && particle.y == y)
				return particle;
		}
		return null;
	}
}
