SELECT * FROM bittrader_data.t_forwardtest_result where config_number = 1 and test_run = 6;
SELECT * FROM bittrader_data.t_forwardtest_result where  test_run = 6;
delete from bittrader_data.t_forwardtest_result;


SELECT avg(average_percent), avg(number_of_trades), avg(total_percent) FROM bittrader_data.t_forwardtest_result where config_number = 6 and test_run = 6;