mvn clean package -DskipTests
docker build . -t centos71.com/library/gateway-kubernetes:1.0

docker push  centos71.com/library/gateway-kubernetes:1.0

kubectl apply -f  config-1-namespace.yml -n my-ns

kubectl delete -f k8s_gateway_deployment.yml -n my-ns
sleep 3
kubectl apply -f k8s_gateway_deployment.yml -n my-ns
  
kubectl logs  -f --tail=500 "$(kubectl get pods -l app=gateway-kubernetes -n my-ns -o name )" -n my-ns | tee gateway-kubernetes.log
