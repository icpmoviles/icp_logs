package es.icp.logs.Firebase;

public interface ListenerFirebase {
    void ok(int code, Object object);
    void error(int code, Object object);
}
