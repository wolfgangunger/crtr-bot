/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.ui;

import com.unw.crypto.chart.AbstractChartPanel;
import javafx.embed.swing.SwingNode;
import javafx.scene.control.Tab;
import javax.swing.JPanel;

/**
 *
 * @author UNGERW
 */
public class TabUtil {

    public static Tab createChartTab(AbstractChartPanel chart, String title) {
        Tab tab = new Tab();
        tab.setText(title);
        SwingNode node = new SwingNode();
        node.setContent(chart);
        tab.setContent(node);
        return tab;
    }

    public static Tab createStrategyTab(JPanel panel, String title) {
        Tab tab = new Tab();
        tab.setText(title);
        SwingNode node = new SwingNode();
        node.setContent(panel);
        tab.setContent(node);
        return tab;

    }

}
