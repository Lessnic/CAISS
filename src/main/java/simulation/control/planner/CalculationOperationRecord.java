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

package simulation.control.planner;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;

import simulation.structures.architecture.CalculationNode;
import simulation.structures.interaction.OperationWithData;

/**
 * @author Sergey Pomelov on 30/05/2016.
 */
@Immutable
@ParametersAreNonnullByDefault
final class CalculationOperationRecord extends OperationRecord {

    private final CalculationNode core;
    private final OperationWithData operationWithData;

    CalculationOperationRecord(long start, long end, @Nullable TimeUnit timeUnit,
                               CalculationNode core, OperationWithData operationWithData) {
        super(start, end, timeUnit);
        this.core = core;
        this.operationWithData = operationWithData;
    }

    @Nonnull
    public CalculationNode getCore() {
        return core;
    }

    @Nonnull
    public OperationWithData getOperationWithData() {
        return operationWithData;
    }

    @Nonnull
    @Override
    public String toString() {
        return "calculation: " + core.getName() + " : " + operationWithData.info() + ' ' + super.toString();
    }
}
