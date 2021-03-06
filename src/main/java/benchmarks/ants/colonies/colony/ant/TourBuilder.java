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

package benchmarks.ants.colonies.colony.ant;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;

import benchmarks.ants.colonies.colony.CachedRawEdgeQualities;
import benchmarks.ants.data.IDistancesData;

/**
 * @author Sergey Pomelov on 29/04/2016.
 * @see RunningAnt
 */
@NotThreadSafe
@ParametersAreNonnullByDefault
final class TourBuilder {

    @Nonnull
    private final IDistancesData graphMatrix;
    @Nonnull
    private final RouteFinder routeFinder;
    @Nonnull
    private final int[] tour;
    @Nonnull
    private final int[] allowedVertexes;
    @Nonnull
    private final boolean[] visited;
    @Nonnull
    private final float[][] trail;

    @Nonnegative
    private final int size;

    TourBuilder(IDistancesData graphMatrix, CachedRawEdgeQualities cachedRawEdgeQualities,
                float[][] trail) {
        this.graphMatrix = graphMatrix;
        this.trail = trail;
        size = graphMatrix.getSize();
        tour = new int[size];
        allowedVertexes = new int[size];
        visited = new boolean[size];
        routeFinder = new RouteFinder(graphMatrix, cachedRawEdgeQualities);
        initPheromones();
    }

    private void initPheromones() {
        for (int i = 0; i < size; i++) {
            visited[i] = false;
        }
    }

    TourData buildUncycledTour() {
        int currentVertex = selectStartVertex();
        long tourLength = 0L;
        boolean success = true;
        for (int i = 1; (i < size) && success; i++) {
            final Optional<Integer> destinationIndex = routeFinder.findNextVertex(currentVertex,
                    trail, visited, allowedVertexes);
            if (destinationIndex.isPresent()) {
                final int dst = goToDestination(destinationIndex.get(), i);
                tourLength += graphMatrix.getDist(currentVertex, dst);
                currentVertex = dst;
            } else {
                tourLength = Integer.MAX_VALUE;
                success = false;
            }
        }
        return new TourData(success, tour, tourLength);
    }

    private int goToDestination(int destinationIdx, int tourStep) {
        final int destination = allowedVertexes[destinationIdx];
        visited[destination] = true;
        tour[tourStep] = destination;
        return destination;
    }

    private int selectStartVertex() {
        final int startVertex = ThreadLocalRandom.current().nextInt(tour.length);
        tour[0] = startVertex;
        visited[startVertex] = true;
        return startVertex;
    }
}
