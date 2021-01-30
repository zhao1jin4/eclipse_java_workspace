mvn clean package -DskipTests
docker build . -t centos71.com/library/cloud-service:1.0

docker push  centos71.com/library/cloud-service:1.0

kubectl apply -f  config-1-namespace.yml -n my-ns

kubectl delete -f k8s_cloud-deployment.yml -n my-ns
sleep 3
kubectl apply -f k8s_cloud-deployment.yml -n my-ns


kubectl delete -f k8s_cloud-service.yml -n my-ns 
kubectl apply -f k8s_cloud-service.yml -n my-ns

#kubectl apply -f config1.yml -n my-ns
#kubectl apply -f config2.yml -n my-ns

kubectl logs  -f --tail=500 "$(kubectl get pods -l app=cloud-service -n my-ns -o name )" -n my-ns | tee k8s-service.log
