/*
 *     Computer and algorithm interaction simulation software (CAISS).
 *     Copyright (C) 2016 Sergey Pomelov.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package benchmarks.ants;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Sergey Pomelov on 06/05/2016.
 */
public class AntsColoniesTest {

    private static final AntsSettings SETTINGS = new AntsSettings(27603, "wi29",
            60_000_000_000L, 1_000_000_000L, 0.1F, 1.0F);
    private static final ColonyRunResult result = AntsColonies.runCalculations(2, 2, SETTINGS);

    @Test
    public void paramsPassedToEnd() {
        assertEquals(2L, result.getAnts());
        assertEquals(2L, result.getColonies());
    }

    @Test
    public void existsResult() {
        assertNotNull(result);
        assertTrue(result.getResult() < Long.MAX_VALUE);
    }

    @Test
    public void antsSmoke() {
        assertTrue(result.getAntRuns() > 0L);
        assertTrue(result.getAvgAntsRunNs() > 0L);
    }

    @Test
    public void exchangesSmoke() {
        assertTrue(result.getExchanges() > 0L);
        assertTrue(result.getAvgExchangeNs() > 0L);
    }
}
