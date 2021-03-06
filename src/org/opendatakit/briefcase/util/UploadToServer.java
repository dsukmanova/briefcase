/*
 * Copyright (C) 2011 University of Washington.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.opendatakit.briefcase.util;

import java.io.File;

import org.opendatakit.briefcase.model.FormStatus;
import org.opendatakit.briefcase.model.ServerConnectionInfo;
import org.opendatakit.briefcase.model.TerminationFuture;

public class UploadToServer implements ITransferToDestAction {

  ServerConnectionInfo destServerInfo;
  TerminationFuture terminationFuture;
  FormStatus status;
  File formDef;
  File formMediaDir;
  
  UploadToServer(ServerConnectionInfo destinationServerInfo, TerminationFuture terminationFuture, File formDefn, FormStatus status) {
    this.destServerInfo = destinationServerInfo;
    this.terminationFuture = terminationFuture;
    this.status = status;
    this.formDef = formDefn;
    
    String mediaName = formDefn.getName();
    mediaName = mediaName.substring(0, mediaName.lastIndexOf(".")) + "-media";
    File mediaDir = new File( formDefn.getParentFile(), mediaName);
    if ( mediaDir.exists() ) {
      this.formMediaDir = mediaDir;
    } else {
      this.formMediaDir = null;
    }
  }
  
  @Override
  public boolean doAction() {

    ServerUploader uploader = new ServerUploader(destServerInfo, terminationFuture);

    return uploader.uploadForm( status, formDef, formMediaDir);
  }

  @Override
  public ServerConnectionInfo getTransferSettings() {
    return destServerInfo;
  }

}
