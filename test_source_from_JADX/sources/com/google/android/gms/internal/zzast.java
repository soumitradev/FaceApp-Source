package com.google.android.gms.internal;

import javax.jmdns.impl.constants.DNSConstants;

public final class zzast {
    public static zzasu<Boolean> zza = zzasu.zza("analytics.service_client_enabled", true, true);
    public static zzasu<Long> zzaa = zzasu.zza("analytics.service_client.idle_disconnect_millis", 10000, 10000);
    public static zzasu<Long> zzab = zzasu.zza("analytics.service_client.connect_timeout_millis", (long) DNSConstants.CLOSE_TIMEOUT, (long) DNSConstants.CLOSE_TIMEOUT);
    public static zzasu<Long> zzac = zzasu.zza("analytics.service_client.reconnect_throttle_millis", 1800000, 1800000);
    public static zzasu<Long> zzad = zzasu.zza("analytics.monitoring.sample_period_millis", 86400000, 86400000);
    public static zzasu<Long> zzae = zzasu.zza("analytics.initialization_warning_threshold", (long) DNSConstants.CLOSE_TIMEOUT, (long) DNSConstants.CLOSE_TIMEOUT);
    private static zzasu<Boolean> zzaf = zzasu.zza("analytics.service_enabled", false, false);
    private static zzasu<Long> zzag = zzasu.zza("analytics.max_tokens", 60, 60);
    private static zzasu<Float> zzah = zzasu.zza("analytics.tokens_per_sec", 0.5f, 0.5f);
    private static zzasu<Integer> zzai = zzasu.zza("analytics.max_stored_hits_per_app", 2000, 2000);
    private static zzasu<Long> zzaj = zzasu.zza("analytics.min_local_dispatch_millis", 120000, 120000);
    private static zzasu<Long> zzak = zzasu.zza("analytics.max_local_dispatch_millis", 7200000, 7200000);
    private static zzasu<Integer> zzal = zzasu.zza("analytics.max_hits_per_request.k", 20, 20);
    private static zzasu<Long> zzam = zzasu.zza("analytics.service_monitor_interval", 86400000, 86400000);
    private static zzasu<String> zzan;
    private static zzasu<Integer> zzao = zzasu.zza("analytics.first_party_experiment_variant", 0, 0);
    private static zzasu<Long> zzap = zzasu.zza("analytics.service_client.second_connect_delay_millis", (long) DNSConstants.CLOSE_TIMEOUT, (long) DNSConstants.CLOSE_TIMEOUT);
    private static zzasu<Long> zzaq = zzasu.zza("analytics.service_client.unexpected_reconnect_millis", 60000, 60000);
    public static zzasu<String> zzb = zzasu.zza("analytics.log_tag", "GAv4", "GAv4-SVC");
    public static zzasu<Integer> zzc = zzasu.zza("analytics.max_stored_hits", 2000, 20000);
    public static zzasu<Integer> zzd = zzasu.zza("analytics.max_stored_properties_per_app", 100, 100);
    public static zzasu<Long> zze = zzasu.zza("analytics.local_dispatch_millis", 1800000, 120000);
    public static zzasu<Long> zzf = zzasu.zza("analytics.initial_local_dispatch_millis", (long) DNSConstants.CLOSE_TIMEOUT, (long) DNSConstants.CLOSE_TIMEOUT);
    public static zzasu<Long> zzg = zzasu.zza("analytics.dispatch_alarm_millis", 7200000, 7200000);
    public static zzasu<Long> zzh = zzasu.zza("analytics.max_dispatch_alarm_millis", 32400000, 32400000);
    public static zzasu<Integer> zzi = zzasu.zza("analytics.max_hits_per_dispatch", 20, 20);
    public static zzasu<Integer> zzj = zzasu.zza("analytics.max_hits_per_batch", 20, 20);
    public static zzasu<String> zzk;
    public static zzasu<String> zzl;
    public static zzasu<String> zzm;
    public static zzasu<String> zzn;
    public static zzasu<Integer> zzo = zzasu.zza("analytics.max_get_length", 2036, 2036);
    public static zzasu<String> zzp = zzasu.zza("analytics.batching_strategy.k", zzasb.BATCH_BY_COUNT.name(), zzasb.BATCH_BY_COUNT.name());
    public static zzasu<String> zzq;
    public static zzasu<Integer> zzr = zzasu.zza("analytics.max_hit_length.k", 8192, 8192);
    public static zzasu<Integer> zzs = zzasu.zza("analytics.max_post_length.k", 8192, 8192);
    public static zzasu<Integer> zzt = zzasu.zza("analytics.max_batch_post_length", 8192, 8192);
    public static zzasu<String> zzu;
    public static zzasu<Integer> zzv = zzasu.zza("analytics.batch_retry_interval.seconds.k", 3600, 3600);
    public static zzasu<Integer> zzw = zzasu.zza("analytics.http_connection.connect_timeout_millis", 60000, 60000);
    public static zzasu<Integer> zzx = zzasu.zza("analytics.http_connection.read_timeout_millis", 61000, 61000);
    public static zzasu<Long> zzy = zzasu.zza("analytics.campaigns.time_limit", 86400000, 86400000);
    public static zzasu<Boolean> zzz = zzasu.zza("analytics.test.disable_receiver", false, false);

    static {
        String str = "http://www.google-analytics.com";
        zzk = zzasu.zza("analytics.insecure_host", str, str);
        str = "https://ssl.google-analytics.com";
        zzl = zzasu.zza("analytics.secure_host", str, str);
        str = "/collect";
        zzm = zzasu.zza("analytics.simple_endpoint", str, str);
        str = "/batch";
        zzn = zzasu.zza("analytics.batching_endpoint", str, str);
        str = zzash.GZIP.name();
        zzq = zzasu.zza("analytics.compression_strategy.k", str, str);
        String str2 = "404,502";
        zzu = zzasu.zza("analytics.fallback_responses.k", str2, str2);
        String str3 = "";
        zzan = zzasu.zza("analytics.first_party_experiment_id", str3, str3);
    }
}
