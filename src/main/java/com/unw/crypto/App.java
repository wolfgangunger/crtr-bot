package com.unw.crypto;

import com.unw.crypto.chart.BollingerBandsChart;
import com.unw.crypto.chart.CandleChart;
import com.unw.crypto.chart.EMARsiChart;
import com.unw.crypto.chart.EMAStoChart;
import com.unw.crypto.chart.MovingAverageChart;
import com.unw.crypto.chart.SimpleClosedPriceChart;
import com.unw.crypto.data.DataLoader;
import com.unw.crypto.strategy.StrategyPanel;
import com.unw.crypto.ui.TabUtil;
import java.time.Instant;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.ta4j.core.TimeSeries;

/**
 * Hello world!
 *
 */
public class App extends Application {

    private BorderPane bp;
    private Scene scene;
    private TextField tf;

    public static void main(String[] args) {
        //Instant instant = Instant.ofEpochMilli(Long.parseLong("1482410598") * 1000);
        //System.out.println(instant.toString());
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initUi(primaryStage);
    }

    private void initUi(Stage primaryStage) {
        BorderPane root = createBorderPane();
        createToolbar(root);
        //
        initTabPane(root);
        //
        createBottomBar(root);

        scene = new Scene(root, Config.WIDTH, Config.HEIGHT);
        primaryStage.setTitle("TradingBot");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private BorderPane createBorderPane() {
        bp = new BorderPane();
        return bp;
    }

    private void createToolbar(BorderPane root) {
        ToolBar tb = new ToolBar();
        initButtons(tb);
        root.setTop(tb);
    }

    private void initButtons(ToolBar tb) {
        Button bttRecord = new Button();
        bttRecord.setText("Record");
        bttRecord.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // recordData();
                System.out.println("Start");
            }
        });
        Button bttStop = new Button();
        bttStop.setText("Stop");
        bttStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Stop");
                //stopRecord();
            }
        });

        Button bttRead = new Button();
        bttRead.setText("Read");
        bttRead.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Read");
                //readData();
            }
        });

        tb.getItems().add(bttRecord);
        tb.getItems().add(bttStop);
        tb.getItems().add(bttRead);

    }

    private void createBottomBar(BorderPane root) {
        tf = new TextField();
        root.setBottom(tf);
    }

    private void initTabPane(BorderPane root) {
        TimeSeries series = DataLoader.loadData();
        TabPane tabPane = new TabPane();
        // charts
        CandleChart candleChart = new CandleChart(series, tabPane);
        tabPane.getTabs().add(TabUtil.createChartTab(candleChart, "Candle"));
        SimpleClosedPriceChart simpleClosedPriceChart = new SimpleClosedPriceChart(series, tabPane);        
        tabPane.getTabs().add(TabUtil.createChartTab(simpleClosedPriceChart, "ClosedPrice"));
        MovingAverageChart movingAverageChart = new MovingAverageChart(series, tabPane);
        tabPane.getTabs().add(TabUtil.createChartTab(movingAverageChart, "MovingAverage"));
        EMARsiChart emaRsiChart = new EMARsiChart(series, tabPane);
        tabPane.getTabs().add(TabUtil.createChartTab(emaRsiChart, "EMA RSI"));
        EMAStoChart emaStoChart = new EMAStoChart(series, tabPane);
        tabPane.getTabs().add(TabUtil.createChartTab(emaStoChart, "EMA STO"));
        BollingerBandsChart bollingerChart = new BollingerBandsChart(series, tabPane);
        tabPane.getTabs().add(TabUtil.createChartTab(bollingerChart, "Bollinger"));        
        //strategy
        StrategyPanel strategyPanel = new StrategyPanel(series);
        tabPane.getTabs().add(TabUtil.createStrategyTab(strategyPanel, "Strategy 1"));        
        //root.setCenter(sc);
        root.setCenter(tabPane);
    }
}
