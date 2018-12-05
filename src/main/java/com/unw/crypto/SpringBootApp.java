package com.unw.crypto;

import com.unw.crypto.chart.BollingerBandsChart;
import com.unw.crypto.chart.CandleChart;
import com.unw.crypto.chart.EMARsiChart;
import com.unw.crypto.chart.EMAStoChart;
import com.unw.crypto.chart.MovingAverageChart;
import com.unw.crypto.chart.SimpleClosedPriceChart;
import com.unw.crypto.loader.TimeSeriesDBLoader;
import com.unw.crypto.model.Currency;
import com.unw.crypto.model.Exchange;
import com.unw.crypto.strategy.StrategyPanel;
import com.unw.crypto.ui.TabUtil;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.ta4j.core.TimeSeries;

/**
 * main class
 *
 */
@SpringBootApplication
public class SpringBootApp extends Application {

    private ConfigurableApplicationContext context;
    private BorderPane bp;
    private Scene scene;
    private TextField tf;
    private DatePicker from;
    private DatePicker until;
    private ComboBox<Currency> cmbCurrency;
    private ComboBox<Exchange> cmbExchange;
    //charts
    private CandleChart candleChart;
    private SimpleClosedPriceChart simpleClosedPriceChart;
    private MovingAverageChart movingAverageChart;
    private EMARsiChart emaRsiChart;
    private EMAStoChart emaStoChart;
    private BollingerBandsChart bollingerChart;
    //strategy
    private StrategyPanel strategyPanel;
    //
    //private DataLoaderDB dataLoader;
    private TimeSeriesDBLoader timeSeriesDBLoader;
    private TimeSeries series;

    public static void main(String[] args) {
        launch(SpringBootApp.class, args);
    }

    @Override
    public void stop() throws Exception {
        context.stop();
    }

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(SpringBootApp.class);
       // dataLoader = context.getBean(DataLoaderDB.class);
        timeSeriesDBLoader = context.getBean(TimeSeriesDBLoader.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initUi(primaryStage);
    }

    private void initUi(Stage primaryStage) {
        System.setProperty("java.awt.headless", "false");
        java.awt.Toolkit.getDefaultToolkit();
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
        initComboAndDate(tb);
        root.setTop(tb);
    }

    private void initButtons(ToolBar tb) {
        Button bttRecord = new Button();
        bttRecord.setText("Load");
        bttRecord.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // recordData();
                System.out.println("Load Data");
                refreshData();
            }
        });
        Button bttStop = new Button();
        bttStop.setText("-");
        bttStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("-");
                //stopRecord();
            }
        });

        Button bttRead = new Button();
        bttRead.setText("-");
        bttRead.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("-");
                //readData();
            }
        });

        tb.getItems().add(bttRecord);
        tb.getItems().add(bttStop);
        tb.getItems().add(bttRead);

    }

    private void initComboAndDate(ToolBar tb) {

        cmbCurrency = new ComboBox();
        cmbCurrency.setValue(Currency.BTC);
        cmbExchange = new ComboBox();
        cmbExchange.setValue(Exchange.COINBASE);
        
        from = new DatePicker();
        from.setValue(LocalDate.now().minusMonths(3));
        until = new DatePicker();
        until.setValue(LocalDate.now());

        tb.getItems().add(cmbCurrency);
        tb.getItems().add(cmbExchange);
        tb.getItems().add(from);
        tb.getItems().add(until);
    }

    private void createBottomBar(BorderPane root) {
        tf = new TextField();
        root.setBottom(tf);
    }

    private void initTabPane(BorderPane root) {
        //DataLoader dataLoader =  context.getBean(DataLoader.class);
        //DataLoaderDB dataLoader = context.getBean(DataLoaderDB.class);
        //series = dataLoader.loadData();
        series = timeSeriesDBLoader.loadDataWithParams(from.getValue(), until.getValue(),cmbCurrency.getValue(), cmbExchange.getValue());
        TabPane tabPane = new TabPane();
        // charts
        candleChart = new CandleChart(series, tabPane);
        tabPane.getTabs().add(TabUtil.createChartTab(candleChart, "Candle"));
        simpleClosedPriceChart = new SimpleClosedPriceChart(series, tabPane);
        tabPane.getTabs().add(TabUtil.createChartTab(simpleClosedPriceChart, "ClosedPrice"));
        movingAverageChart = new MovingAverageChart(series, tabPane);
        tabPane.getTabs().add(TabUtil.createChartTab(movingAverageChart, "MovingAverage"));
        emaRsiChart = new EMARsiChart(series, tabPane);
        tabPane.getTabs().add(TabUtil.createChartTab(emaRsiChart, "EMA RSI"));
        emaStoChart = new EMAStoChart(series, tabPane);
        tabPane.getTabs().add(TabUtil.createChartTab(emaStoChart, "EMA STO"));
        bollingerChart = new BollingerBandsChart(series, tabPane);
        tabPane.getTabs().add(TabUtil.createChartTab(bollingerChart, "Bollinger"));
        //strategy
        strategyPanel = new StrategyPanel(series);
        tabPane.getTabs().add(TabUtil.createStrategyTab(strategyPanel, "Strategy"));
        //root.setCenter(sc);
        root.setCenter(tabPane);
    }

    private void refreshData() {
        series = timeSeriesDBLoader.loadDataWithParams(from.getValue(), until.getValue(),cmbCurrency.getValue(), cmbExchange.getValue());
        
        candleChart.setSeries(series);
        candleChart.refresh();

        simpleClosedPriceChart.setSeries(series);
        simpleClosedPriceChart.refresh();

        movingAverageChart.setSeries(series);
        movingAverageChart.refresh();

        emaRsiChart.setSeries(series);
        emaRsiChart.refresh();

        bollingerChart.setSeries(series);
        bollingerChart.refresh();

        strategyPanel.setSeries(series);
        //strategyPanel.refresh();
    }
}
