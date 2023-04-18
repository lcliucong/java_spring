<?php


namespace app\api\service\Orders;

use app\api\service\Orders\DyUserOrders\DyUserOrderService;
use app\api\service\Orders\JdOrders\JdOrderService;
use app\api\service\Orders\MangoOrders\MangoOrderService;
use app\api\service\Orders\PddOrders\PddOrderService;
use app\api\service\Orders\TbOrders\TbOrderService;
use app\api\service\Orders\VphOrders\VphOrderService;

class OrdersAccessService
{
    private static $instance;

    public static function GetInstance(): OrdersAccessService
    {
        if (!self::$instance instanceof OrdersAccessService) {
            self::$instance = new self();
        }
        return self::$instance;
    }

    public function OrdersAccess($union_type, $msg_body)
    {
        foreach ($msg_body['unionList'] as $item) {
            $unionInfoArr = json_decode($item, 1);
            switch ($union_type) {
                case "tb":
                    $start_time = date('Y-m-d H:i:s', $msg_body['sync_start_timestamp']);
                    $end_time = date('Y-m-d H:i:s', $msg_body['sync_end_timestamp']);
//                    TbOrderService::GetInstance()->FullOrders($unionInfoArr, $start_time, $end_time, 2, 1);
//                    TbOrderService::GetInstance()->FullOrders($unionInfoArr, $start_time, $end_time, 2);
//                    TbOrderService::GetInstance()->FullOrders($unionInfoArr, $start_time, $end_time, 3, 1);
//                    TbOrderService::GetInstance()->FullOrders($unionInfoArr, $start_time, $end_time, 3);
//                    TbOrderService::GetInstance()->FullOrders($unionInfoArr, $start_time, $end_time, 4, 1);
//                    TbOrderService::GetInstance()->FullOrders($unionInfoArr, $start_time, $end_time, 4);
                    TbOrderService::GetInstance()->FullRefundOrders($unionInfoArr, $start_time, $end_time, 5);
                    TbOrderService::GetInstance()->FullRefundOrders($unionInfoArr, $start_time, $end_time, 3);
                    break;
                case "jd":
                    $start_time = date('Y-m-d H:i:s', $msg_body['sync_start_timestamp']);
                    $end_time = date('Y-m-d H:i:s', $msg_body['sync_end_timestamp']);
                    JdOrderService::GetInstance()->FullOrders($unionInfoArr, $start_time, $end_time, 1);
                    JdOrderService::GetInstance()->FullOrders($unionInfoArr, $start_time, $end_time, 2);
                    JdOrderService::GetInstance()->FullOrders($unionInfoArr, $start_time, $end_time, 3);
                    break;
                case "pdd":
                    PddOrderService::GetInstance()->FullOrders($unionInfoArr, 1, $msg_body['sync_start_timestamp'], $msg_body['sync_end_timestamp']);
                    break;
                case "vph":
                    VphOrderService::GetInstance()->FullOrders($unionInfoArr, $msg_body['sync_start_timestamp'], $msg_body['sync_end_timestamp']);
                    VphOrderService::GetInstance()->FullOrders($unionInfoArr, $msg_body['sync_start_timestamp'], $msg_body['sync_end_timestamp'],1);
                    break;
                case "dy_user":
                    $start_time = date('Y-m-d H:i:s', $msg_body['sync_start_timestamp']);
                    $end_time = date('Y-m-d H:i:s', $msg_body['sync_end_timestamp']);
                    DyUserOrderService::GetInstance()->FullOrders($unionInfoArr, $start_time, $end_time, 0, 'pay');
                    DyUserOrderService::GetInstance()->FullOrders($unionInfoArr, $start_time, $end_time, 0, 'update');
                    break;
                case "mango_fans":
                    $data=array(
                        'sync_start_timestamp'=>$msg_body['sync_start_timestamp'],
//                        'sync_start_timestamp'=>1664506800,
                        'sync_end_timestamp'=>$msg_body['sync_end_timestamp'],
//                        'sync_end_timestamp'=>1664510400,
                        'fans_info'=>$item,
                        'page_size'=>2,
                        'page'=>1
                    );
                    MangoOrderService::GetInstance()->FullOrders($data);
                    break;
            }
        }
    }

    public static function LongOrdersAccess($union_type, $msg_body)
    {
        foreach ($msg_body['unionList'] as $item) {
            $unionInfoArr = json_decode($item, 1);
            switch ($union_type) {
                case "tb":
                    $end_timestamp = time();
                    $mark_timestamp = $end_timestamp - 1296000;
                    while ($end_timestamp > $mark_timestamp) {
                        $sync_time = 3600;
                        $start_timestamp = $end_timestamp - $sync_time;
                        $start_time = date('Y-m-d H:i:s', $start_timestamp);
                        $end_time = date('Y-m-d H:i:s', $end_timestamp);
                        TbOrderService::GetInstance()->FullOrders($unionInfoArr, $start_time, $end_time, 2, 1);
                        TbOrderService::GetInstance()->FullOrders($unionInfoArr, $start_time, $end_time, 3, 1);
                        TbOrderService::GetInstance()->FullOrders($unionInfoArr, $start_time, $end_time, 2);
                        TbOrderService::GetInstance()->FullOrders($unionInfoArr, $start_time, $end_time, 3);
                        $end_timestamp = $start_timestamp;
                    }
                    break;
                case "jd":
                    $end_timestamp = time();
                    $mark_timestamp = $end_timestamp - 1296000;
                    while ($end_timestamp > $mark_timestamp) {
                        $sync_time = 3600;
                        $start_timestamp = $end_timestamp - $sync_time;
                        $start_time = date('Y-m-d H:i:s', $start_timestamp);
                        $end_time = date('Y-m-d H:i:s', $end_timestamp);
                        JdOrderService::GetInstance()->FullOrders($unionInfoArr, $start_time, $end_time, 1);
                        JdOrderService::GetInstance()->FullOrders($unionInfoArr, $start_time, $end_time, 2);
                        JdOrderService::GetInstance()->FullOrders($unionInfoArr, $start_time, $end_time, 3);
                        $end_timestamp = $start_timestamp;
                    }
                    break;
                case "pdd":
                    $end_timestamp = time();
                    $mark_timestamp = $end_timestamp - 1296000;
                    while ($end_timestamp > $mark_timestamp) {
                        $start_timestamp = $end_timestamp - 1800;
                        PddOrderService::GetInstance()->FullOrders($unionInfoArr, 1, $start_timestamp, $end_timestamp);
                        $end_timestamp = $start_timestamp;
                    }
                    break;
            }
        }
    }

    public static function RetryOrdersAccess($union_type, $msg_body)
    {
        switch ($union_type) {
            case "tb":
                TbOrderService::GetInstance()->FullOrders($msg_body['auth_info'], $msg_body['sync_start_time'], $msg_body['sync_end_time'], $msg_body['query_type'], $msg_body['sync_type'], 1, $msg_body['retry'] ?? 0);
                break;
            case "jd":
                //TODO 京东订单同步
                JdOrderService::GetInstance()->FullOrders($msg_body['auth_info'], $msg_body['sync_start_time'], $msg_body['sync_end_time'], $msg_body['type']);
                break;
            case "pdd":
                //TODO 拼多多订单同步
                PddOrderService::GetInstance()->FullOrders($msg_body['auth_info'], 1, $msg_body['sync_start_timestamp'], $msg_body['sync_end_timestamp']);
                break;
        }
    }

    private static function GetTbSyncTime(): int
    {
        $config_time = config('sync_config.tb.short_time');
        $month = date("m", time());
        if (in_array($month, $config_time)) {
            return 1200;
        } else {
            return 3600;
        }
    }
}