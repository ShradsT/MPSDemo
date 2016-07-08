/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.demo.matlabpoc;
import com.mathworks.mps.client.MATLABException;

import java.io.IOException;
import java.util.ArrayList;
public interface CleanData {
	Object[] DataCleansing(int num_args, String stringToClean)
  throws IOException, MATLABException;
	
	
}