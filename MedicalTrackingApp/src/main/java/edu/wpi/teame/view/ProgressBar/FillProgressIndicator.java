/*
 * Copyright (c) 2014, Andrea Vacondio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.wpi.teame.view.ProgressBar;

import edu.wpi.teame.App;
import edu.wpi.teame.model.enums.ServiceRequestPriority;
import javafx.scene.control.Skin;

public class FillProgressIndicator extends ProgressCircleIndicator {

  public FillProgressIndicator() {
    this.getStylesheets().add(App.class.getResource("css/fillprogress1.css").toString());
    this.getStyleClass().add("fillindicator");
    this.setInnerCircleRadius(20);
  }

  public FillProgressIndicator(ServiceRequestPriority priority) {
    this.getStylesheets()
        .add(
            App.class
                .getResource("css/fillprogress" + String.valueOf(priority.ordinal()) + ".css")
                .toString());
    this.getStyleClass().add("fillindicator");
    this.setInnerCircleRadius(20);
  }

  @Override
  protected Skin<?> createDefaultSkin() {
    return new FillProgressIndicatorSkin(this);
  }
}
