# spark-redis
 ensure good connectivity between Apache Spark and Redis "dataframe creation (key, value) / save result / read result / validate"

#	Application Interaction Spark/Redis

Cette application a pour but tester les interactions entre Apache Spark et Redis, elle permet :
-	La création d’un RDD Spark qui contient des couples (Key, Value).
-	L’écriture du RDD créer dans Redis.
-	La lecture des données écrit en s’appuyant sur les Keys.
-	La vérification des données écrit/lu.
-	Générer un fichier résultat en format JSON, qui contient les informations du test et son résultat. 

#	Composants concernés


Composant	   	Version
- Spark		2.3.2
- Redis		4.0.14
- DC-OS		1.12
- Scala		2.11.8
- Assembly		0.14.9



# Prérequis 
Avant de lancer l’application vous devez la configurer, cela se fait au niveau du fichier de configuration de l’application, qui est dans le chemin (/src/main/resources/Redis.conf).


# Traitements 
-	Lancer l’application sur le dcos bootstrap avec la commande 
dcos spark --name="spark-2-3" run --submit-args="--conf spark.mesos.containerizer=docker --conf spark.driver.memory=4G --conf spark.cores.max=3 --conf spark.executor.cores=1 --conf spark.executor.memory=4G --conf spark.mesos.executor.docker.forcePullImage=false --conf spark.eventLog.enabled=true --conf spark.eventLog.dir=hdfs:///spark_history  --class sparkRedis hdfs:///jars/Spark-Redis -assembly-0.1.jar"
 
# Validation du test 
Vérifier le statut du test dans le fichier résultat. 
