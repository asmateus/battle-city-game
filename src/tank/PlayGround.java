/*
 * Copyright (c) 2016, asmateus
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package tank;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author asmateus
 * PlayGround class has to update its own elements, for that it counts of tree
 * different kind of graphs, all provided by GraphDescriptor Class.
 * PlayGround contains the following types of elements:
 *  + Semi static elements such as bricks, steel, iron, eagle, flags and forest,
 *    this elements are painted last so that they will be on top, and they have a
 *    GridLayout layout.
 *  + Static elements such as void paths, water and bedrock are painted first so
 *    that they be on the bottom.
 *  + Dynamic elements such as bullets, tanks, gifts, tanks_information, explosions, 
 *    kill points, etc. are drawn in the middle, so that they be on top of static
 *    elements and bellow the semi static ones.
 * Layout structure:
 *  JLayeredPane
 *  | Layer 1: JPanel -> GridLayout, non opaque
 *  | Layer 2: JPanel -> Null, non opaque
 *  | Layer 3: JPanel -> GridLayout, non opaque
 */

public class PlayGround extends JLayeredPane
{
    private final JPanel paths;
    private final JPanel blocks;
    private final JPanel toys;
    
    private final GridBagConstraints c = new GridBagConstraints();
    private final List<List<BlockDescriptor>> bg;
    
    public PlayGround(GraphDescriptor gd)
    {
        super.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
        super.setPreferredSize(new Dimension(552,488));
        
        paths = new JPanel(new GridLayout(15, 17));
        blocks = new JPanel(new GridLayout(15, 17));
        toys = new JPanel();
        
        // Manually set bounds
        this.setBound();
        
        this.bg = gd.block_graph;
        
        this.setGBC();
        this.fillPlayGround();
        
        super.setVisible(true);
    }
    
    private void setBound()
    {
        paths.setBounds(4, 4, 544, 480);
        blocks.setBounds(4, 4, 544, 480);
        toys.setBounds(4, 4, 544, 480);
    }
    
    // GridBagConstraints of PlayGround for setting it in GameArea.
    private void setGBC()
    {
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 20;
        c.ipady = 0;
        c.ipadx = 0;
    }
    
    private void fillPlayGround()
    {
        // block graph will divide its elements in blocks and paths containers
        for(int i = 0; i < bg.size(); ++i) {
            for(int j = 0; j < bg.get(i).size(); ++j)
                blocks.add(bg.get(i).get(j));
        }
    }
    
    public GridBagConstraints getPGConstraints()
    {
        return c;
    }
    
    public void startPG()
    {        
        this.add(blocks, new Integer(0));
        
    }
}