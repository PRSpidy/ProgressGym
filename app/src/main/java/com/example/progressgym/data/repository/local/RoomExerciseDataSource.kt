package com.example.progressgym.data.repository.local

import android.util.Log
import com.example.progressgym.MyApp
import com.example.progressgym.data.model.Exercise
import com.example.progressgym.data.model.ExerciseSpinner
import com.example.progressgym.data.model.Muscle
import com.example.progressgym.data.model.MuscleSpinner
import com.example.progressgym.data.model.Training
import com.example.progressgym.data.repository.CommonExerciseRepository
import com.example.progressgym.data.repository.local.dao.DaoExercise
import com.example.progressgym.data.repository.local.dao.DaoMuscle
import com.example.progressgym.data.repository.local.dao.DaoTraining
import com.example.progressgym.data.repository.local.tables.RoomExercise
import com.example.progressgym.data.repository.local.tables.RoomMuscle
import com.example.progressgym.utils.Resource
import java.util.Date

class RoomExerciseDataSource: CommonExerciseRepository {
    private val daoExercise: DaoExercise = MyApp.db.daoExercise()
    private val daoMuscle: DaoMuscle = MyApp.db.daoMuscle()

    override suspend fun getExercisesFromTraining(trainingId: Int): Resource<List<Exercise>> {
        try {
            val exercise = daoExercise.getAllExercisesByTrainingPlanId(trainingId)
            val listExercise = mutableListOf<Exercise>()

            exercise.forEach {

                val muscle = daoMuscle.getMuscleById(it.muscleId)
                if(muscle.isNotEmpty()){
                    listExercise.add(
                        Exercise(it.id, it.name, Muscle(muscle.first().id, muscle.first().name))
                    )
                }
            }
            return Resource.success(listExercise)
        }catch (e: Exception){
            return Resource.error("Error getting exercises: ${e.message}")
        }
    }

    override suspend fun getAllExercisesOfEachMuscle(): Resource<List<MuscleSpinner>> {
        try {
            val allMuscles = daoMuscle.getAllMuscle()
            val listMuscles = mutableListOf<MuscleSpinner>()

            allMuscles.forEach {
                val exercisesByMuscle = daoExercise.getAllExercisesByMuscleId(it.id)

                val listExercises = mutableListOf<ExerciseSpinner>()
                exercisesByMuscle.forEach{
                    listExercises.add(
                        ExerciseSpinner(it.id, it.name)
                    )
                }

                listMuscles.add(
                    MuscleSpinner(it.id, it.name, listExercises)
                )
            }
            return Resource.success(listMuscles)
        }catch (e: Exception){
            return Resource.error("Error getting exercises: ${e.message}")
        }
    }

    override suspend fun getAllMuscle(): Resource<List<Muscle>> {
        try {
            val allMuscles = daoMuscle.getAllMuscle()
            val listMuscles = mutableListOf<Muscle>()
            allMuscles.forEach{
                listMuscles.add(Muscle(it.id, it.name))
            }

            return Resource.success(listMuscles)
        }catch (e: Exception){
            return Resource.error("Error getting muscles: ${e.message}")
        }
    }

    override suspend fun getAllExercises(trainingId: Int): Resource<List<Exercise>> {
        try {

            val allExercise = daoExercise.getAllExercises(trainingId)
            val listExercise = mutableListOf<Exercise>()
            allExercise.forEach{

                var muscleRoom = daoMuscle.getMuscleById(it.muscleId).first()

                listExercise.add(Exercise(it.id, it.name, Muscle(muscleRoom.id, muscleRoom.name)))
            }
            return Resource.success(listExercise)
        }catch (e: Exception){
            return Resource.error("Error getting exercises: ${e.message}")
        }
    }

    override suspend fun addExercisesToTraining(
        trainingId: Int,
        exerciseId: Int
    ): Resource<Boolean> {
        try {
            daoExercise.addExerciseToTraining(trainingId, exerciseId, Date())
            return Resource.success(true)
        }catch (e: Exception){
            return Resource.error("Error adding exercise to training : ${e.message}")
        }
    }

    suspend fun addAllExercises(): Boolean{
        try {

            //Create muscles
            val pectoral = RoomMuscle(0, "Pectoral", Date())
            val triceps = RoomMuscle(0, "Triceps", Date())
            val gemelos = RoomMuscle(0, "Gemelos", Date())
            val espalda = RoomMuscle(0, "Espalda", Date())
            val biceps = RoomMuscle(0, "Biceps", Date())
            val abdominales = RoomMuscle(0, "Abdominales", Date())
            val hombros = RoomMuscle(0, "Hombros", Date())
            val pierna = RoomMuscle(0, "Pierna", Date())

            //Insert muscles bd
            val pectoralId = daoMuscle.insertMuscle(pectoral).toInt()
            val tricepsId = daoMuscle.insertMuscle(triceps).toInt()
            val gemelosId = daoMuscle.insertMuscle(gemelos).toInt()
            val espaldaId = daoMuscle.insertMuscle(espalda).toInt()
            val bicepsId = daoMuscle.insertMuscle(biceps).toInt()
            val abdominalesId = daoMuscle.insertMuscle(abdominales).toInt()
            val hombrosId = daoMuscle.insertMuscle(hombros).toInt()
            val piernaId = daoMuscle.insertMuscle(pierna).toInt()

            pectoralExercises(pectoralId)

            tricepsExercises(tricepsId)

            gemelosExercises(gemelosId)

            espaldaExercises(espaldaId)

            bicepsExercises(bicepsId)

            absExercises(abdominalesId)

            hombrosExercises(hombrosId)

            piernaExercises(piernaId)

            return true
        }catch (e: Exception){
            return false;
        }
    }

    private suspend fun bicepsExercises(bicepsId: Int) {
        // Biceps exercises
        val curlBarra = RoomExercise(0, "Curl con barra", Date(), bicepsId)
        daoExercise.insertExercise(curlBarra)

        val curlAlternoMancuernas = RoomExercise(0, "Curl alterno con mancuernas", Date(), bicepsId)
        daoExercise.insertExercise(curlAlternoMancuernas)

        val curlCuerdaPolea = RoomExercise(0, "Curl con cuerda en polea", Date(), bicepsId)
        daoExercise.insertExercise(curlCuerdaPolea)

        val curlBarraEZ = RoomExercise(0, "Curl con barra EZ", Date(), bicepsId)
        daoExercise.insertExercise(curlBarraEZ)

        val curlPredicadorBarraEZ = RoomExercise(0, "Curl de predicador con barra EZ", Date(), bicepsId)
        daoExercise.insertExercise(curlPredicadorBarraEZ)

        val curlAlternoMartilloMancuernas = RoomExercise(0, "Curl alterno de martillo con mancuernas", Date(), bicepsId)
        daoExercise.insertExercise(curlAlternoMartilloMancuernas)

        val curlInclinadoMancuernas = RoomExercise(0, "Curl inclinado con mancuernas", Date(), bicepsId)
        daoExercise.insertExercise(curlInclinadoMancuernas)

        val curlConcentradoMancuernas = RoomExercise(0, "Curl concentrado con mancuernas", Date(), bicepsId)
        daoExercise.insertExercise(curlConcentradoMancuernas)

        val curlCableBajaUnaMano = RoomExercise(0, "Curl de cable en polea baja a una mano", Date(), bicepsId)
        daoExercise.insertExercise(curlCableBajaUnaMano)

        val curlCableBarraRectaBaja = RoomExercise(0, "Curl de cable con barra recta en polea baja", Date(), bicepsId)
        daoExercise.insertExercise(curlCableBarraRectaBaja)

        val curlCableAltaPie = RoomExercise(0, "Curl de cable en polea alta de pie", Date(), bicepsId)
        daoExercise.insertExercise(curlCableAltaPie)

        val curlMuñecaBarraSentado = RoomExercise(0, "Curl de muñeca con barra sentado", Date(), bicepsId)
        daoExercise.insertExercise(curlMuñecaBarraSentado)

        val extensionMuñecaBarraSentado = RoomExercise(0, "Extensión de muñeca con barra sentado", Date(), bicepsId)
        daoExercise.insertExercise(extensionMuñecaBarraSentado)

        val curlBarraInvertido = RoomExercise(0, "Curl de barra invertido", Date(), bicepsId)
        daoExercise.insertExercise(curlBarraInvertido)

    }

    private suspend fun absExercises(abdominalesId: Int) {
        // Abdominales exercises
        val crunch = RoomExercise(0, "Crunch", Date(), abdominalesId)
        daoExercise.insertExercise(crunch)

        val crunchOblicuo = RoomExercise(0, "Crunch oblicuo", Date(), abdominalesId)
        daoExercise.insertExercise(crunchOblicuo)

        val abdominalesMaquina = RoomExercise(0, "Abdominales en máquina", Date(), abdominalesId)
        daoExercise.insertExercise(abdominalesMaquina)

        val abdominalesCuerdaPoleaAlta = RoomExercise(0, "Abdominales con cuerda en polea alta", Date(), abdominalesId)
        daoExercise.insertExercise(abdominalesCuerdaPoleaAlta)

        val plancha = RoomExercise(0, "Plancha", Date(), abdominalesId)
        daoExercise.insertExercise(plancha)

        val elevacionPiernas = RoomExercise(0, "Elevación de piernas", Date(), abdominalesId)
        daoExercise.insertExercise(elevacionPiernas)

        val encogimientosRodillas = RoomExercise(0, "Encogimientos de rodillas para abdominales", Date(), abdominalesId)
        daoExercise.insertExercise(encogimientosRodillas)

        val abdominalesBrazosEstirados = RoomExercise(0, "Abdominales con brazos estirados", Date(), abdominalesId)
        daoExercise.insertExercise(abdominalesBrazosEstirados)

        val planchaFlexion = RoomExercise(0, "Plancha con flexión", Date(), abdominalesId)
        daoExercise.insertExercise(planchaFlexion)

    }

    private suspend fun hombrosExercises(hombrosId: Int) {
        // Hombros exercises
        val pressHombroMancuernas = RoomExercise(0, "Press de hombro con mancuernas", Date(), hombrosId)
        daoExercise.insertExercise(pressHombroMancuernas)

        val elevacionLateralMancuernas = RoomExercise(0, "Elevación lateral con mancuernas", Date(), hombrosId)
        daoExercise.insertExercise(elevacionLateralMancuernas)

        val elevacionFrontalMancuernas = RoomExercise(0, "Elevación frontal con mancuernas", Date(), hombrosId)
        daoExercise.insertExercise(elevacionFrontalMancuernas)

        val crucesInversosPoleaAlta = RoomExercise(0, "Cruces inversos en polea alta", Date(), hombrosId)
        daoExercise.insertExercise(crucesInversosPoleaAlta)

        val pressHombrosMaquinaSmith = RoomExercise(0, "Press de hombros en Máquina Smith", Date(), hombrosId)
        daoExercise.insertExercise(pressHombrosMaquinaSmith)

        val remoAltoBarra = RoomExercise(0, "Remo alto con barra", Date(), hombrosId)
        daoExercise.insertExercise(remoAltoBarra)

        val elevacionesPosterioresPajaro = RoomExercise(0, "Elevaciones posteriores para hombros 'Pájaro'", Date(), hombrosId)
        daoExercise.insertExercise(elevacionesPosterioresPajaro)

        val elevacionLateralCableUnaMano = RoomExercise(0, "Elevación lateral con cable a una mano", Date(), hombrosId)
        daoExercise.insertExercise(elevacionLateralCableUnaMano)

        val pressMilitarMancuernas = RoomExercise(0, "Press Militar con mancuernas", Date(), hombrosId)
        daoExercise.insertExercise(pressMilitarMancuernas)

        val pressMilitar = RoomExercise(0, "Press Militar", Date(), hombrosId)
        daoExercise.insertExercise(pressMilitar)

        val elevacionesFrontalesCableUnaMano = RoomExercise(0, "Elevaciones frontales con cable a una mano", Date(), hombrosId)
        daoExercise.insertExercise(elevacionesFrontalesCableUnaMano)

        val elevacionesFrontalesBarra = RoomExercise(0, "Elevaciones frontales con barra", Date(), hombrosId)
        daoExercise.insertExercise(elevacionesFrontalesBarra)

        val pressMilitarSentadoBarra = RoomExercise(0, "Press militar sentado con barra", Date(), hombrosId)
        daoExercise.insertExercise(pressMilitarSentadoBarra)

        val pressTrasNucaSentado = RoomExercise(0, "Press tras nuca sentado", Date(), hombrosId)
        daoExercise.insertExercise(pressTrasNucaSentado)

        val pressMilitarPie = RoomExercise(0, "Press militar de pie", Date(), hombrosId)
        daoExercise.insertExercise(pressMilitarPie)

        val pressMilitarPieTrasNuca = RoomExercise(0, "Press militar de pie tras nuca", Date(), hombrosId)
        daoExercise.insertExercise(pressMilitarPieTrasNuca)

        val elevacionFrontalMancuernasAgarreNeutro = RoomExercise(0, "Elevación frontal con mancuernas alternas agarre neutro", Date(), hombrosId)
        daoExercise.insertExercise(elevacionFrontalMancuernasAgarreNeutro)

        val elevacionFrontalPoleaBajaAgarreNeutro = RoomExercise(0, "Elevación frontal con un brazo en polea baja agarre neutro", Date(), hombrosId)
        daoExercise.insertExercise(elevacionFrontalPoleaBajaAgarreNeutro)

        val elevacionFrontalMancuernaDosManos = RoomExercise(0, "Elevación frontal con mancuerna a dos manos", Date(), hombrosId)
        daoExercise.insertExercise(elevacionFrontalMancuernaDosManos)

    }

    private suspend fun piernaExercises(piernaId: Int) {
        // Pierna exercises
        val sentadilla = RoomExercise(0, "Sentadilla", Date(), piernaId)
        daoExercise.insertExercise(sentadilla)

        val prensaPiernas = RoomExercise(0, "Prensa de piernas", Date(), piernaId)
        daoExercise.insertExercise(prensaPiernas)

        val extensionPiernas = RoomExercise(0, "Extensión de piernas", Date(), piernaId)
        daoExercise.insertExercise(extensionPiernas)

        val zancada = RoomExercise(0, "Zancada", Date(), piernaId)
        daoExercise.insertExercise(zancada)

        val curlPiernaTumbadoMaquinaFemoral = RoomExercise(0, "Curl de pierna tumbado en máquina de femoral", Date(), piernaId)
        daoExercise.insertExercise(curlPiernaTumbadoMaquinaFemoral)

        val sentadillaHack = RoomExercise(0, "Sentadilla Hack", Date(), piernaId)
        daoExercise.insertExercise(sentadillaHack)

        val curlPiernasSentado = RoomExercise(0, "Curl de piernas sentado", Date(), piernaId)
        daoExercise.insertExercise(curlPiernasSentado)

        val extensionUnaPierna = RoomExercise(0, "Extensión a una pierna", Date(), piernaId)
        daoExercise.insertExercise(extensionUnaPierna)

        val sentadillaFrontal = RoomExercise(0, "Sentadilla frontal", Date(), piernaId)
        daoExercise.insertExercise(sentadillaFrontal)

        val pesoMuertoRumanoMancuernasPiernasRectas = RoomExercise(0, "Peso muerto rumano (piernas rectas) con mancuernas", Date(), piernaId)
        daoExercise.insertExercise(pesoMuertoRumanoMancuernasPiernasRectas)

        val pesoMuertoRumanoBarraPiernasRectas = RoomExercise(0, "Peso muerto rumano (piernas rectas) con barra", Date(), piernaId)
        daoExercise.insertExercise(pesoMuertoRumanoBarraPiernasRectas)

        val sentadillaGobletMancuerna = RoomExercise(0, "Sentadilla Goblet con mancuerna", Date(), piernaId)
        daoExercise.insertExercise(sentadillaGobletMancuerna)

        val saltoRodillasAlPecho = RoomExercise(0, "Salto Rodillas al Pecho", Date(), piernaId)
        daoExercise.insertExercise(saltoRodillasAlPecho)

        val burpees = RoomExercise(0, "Burpees", Date(), piernaId)
        daoExercise.insertExercise(burpees)

        val sentadillasPropioPeso = RoomExercise(0, "Sentadillas con propio peso", Date(), piernaId)
        daoExercise.insertExercise(sentadillasPropioPeso)

        val repeticionesSentadillasPropioPeso = RoomExercise(0, "1.5 repeticiones de sentadillas con propio peso", Date(), piernaId)
        daoExercise.insertExercise(repeticionesSentadillasPropioPeso)

        val sentadillasBalonMedicinal = RoomExercise(0, "Sentadillas con balón medicinal", Date(), piernaId)
        daoExercise.insertExercise(sentadillasBalonMedicinal)

        val sentadillaBulgaraBarra = RoomExercise(0, "Sentadilla búlgara con barra", Date(), piernaId)
        daoExercise.insertExercise(sentadillaBulgaraBarra)

        val sentadillaBulgaraPropioPeso = RoomExercise(0, "Sentadilla búlgara con propio peso", Date(), piernaId)
        daoExercise.insertExercise(sentadillaBulgaraPropioPeso)

        val sentadillaMiniBanda = RoomExercise(0, "Sentadilla con mini banda", Date(), piernaId)
        daoExercise.insertExercise(sentadillaMiniBanda)

        val sentadillaSalto = RoomExercise(0, "Sentadilla con salto", Date(), piernaId)
        daoExercise.insertExercise(sentadillaSalto)

        val sentadillaIsometricaPared = RoomExercise(0, "Sentadilla isométrica apoyado sobre la pared", Date(), piernaId)
        daoExercise.insertExercise(sentadillaIsometricaPared)

        val pesoMuertoBalonMedicinal = RoomExercise(0, "Peso muerto con balón medicinal", Date(), piernaId)
        daoExercise.insertExercise(pesoMuertoBalonMedicinal)

        val pesoMuertoUnaPierna = RoomExercise(0, "Peso muerto a una pierna", Date(), piernaId)
        daoExercise.insertExercise(pesoMuertoUnaPierna)

        val sentadillaSumoKettlebell = RoomExercise(0, "Sentadilla sumo con kettlebell", Date(), piernaId)
        daoExercise.insertExercise(sentadillaSumoKettlebell)

        val buenosDiasBarra = RoomExercise(0, "Ejercicio 'buenos días' con barra", Date(), piernaId)
        daoExercise.insertExercise(buenosDiasBarra)

        val puentePropioPeso = RoomExercise(0, "Puente con propio peso", Date(), piernaId)
        daoExercise.insertExercise(puentePropioPeso)

        val puentesUnaPierna = RoomExercise(0, "Puentes a una pierna", Date(), piernaId)
        daoExercise.insertExercise(puentesUnaPierna)

        val puenteBandas = RoomExercise(0, "Puente con bandas", Date(), piernaId)
        daoExercise.insertExercise(puenteBandas)

        val caminataPato = RoomExercise(0, "Caminata de pato", Date(), piernaId)
        daoExercise.insertExercise(caminataPato)

        val supermanCuadrupedia = RoomExercise(0, "Ejercicio Superman en cuadrupedia", Date(), piernaId)
        daoExercise.insertExercise(supermanCuadrupedia)

        val groiners = RoomExercise(0, "Los Groiners", Date(), piernaId)
        daoExercise.insertExercise(groiners)

        val hidrantes = RoomExercise(0, "Hidrantes", Date(), piernaId)
        daoExercise.insertExercise(hidrantes)

        val elevacionesCaderaMaquinaSmith = RoomExercise(0, "Elevaciones de cadera con maquina Smith", Date(), piernaId)
        daoExercise.insertExercise(elevacionesCaderaMaquinaSmith)

        val elevacionesCaderaBarra = RoomExercise(0, "Elevaciones de cadera con barra", Date(), piernaId)
        daoExercise.insertExercise(elevacionesCaderaBarra)

        val abduccionesCaderaSentadoBanda = RoomExercise(0, "Abducciones de cadera sentado con banda", Date(), piernaId)
        daoExercise.insertExercise(abduccionesCaderaSentadoBanda)

        val abduccionCaderaMaquinaAbduccion = RoomExercise(0, "Abducción de cadera con máquina de abducción de cadera", Date(), piernaId)
        daoExercise.insertExercise(abduccionCaderaMaquinaAbduccion)

        val abduccionCablePolea = RoomExercise(0, "Abducción con polea", Date(), piernaId)
        daoExercise.insertExercise(abduccionCablePolea)

        val elevacionesPosicionRanaPropioPeso = RoomExercise(0, "Elevaciones en posición de rana con propio peso", Date(), piernaId)
        daoExercise.insertExercise(elevacionesPosicionRanaPropioPeso)

        val elevacionesCortasPosicionRanaMaquinaSmith = RoomExercise(0, "Elevaciones cortas en posición de rana con maquina Smith", Date(), piernaId)
        daoExercise.insertExercise(elevacionesCortasPosicionRanaMaquinaSmith)

        val almejasLateralesBanda = RoomExercise(0, "Almejas laterales con banda", Date(), piernaId)
        daoExercise.insertExercise(almejasLateralesBanda)

        val levantamientoPiernaAcostadoLado = RoomExercise(0, "Levantamiento de pierna acostado de lado", Date(), piernaId)
        daoExercise.insertExercise(levantamientoPiernaAcostadoLado)

        val elevacionesBicepsFemoralMaquinaGHD = RoomExercise(0, "Elevaciones de bíceps femoral con máquina GHD", Date(), piernaId)
        daoExercise.insertExercise(elevacionesBicepsFemoralMaquinaGHD)

        val stepUpMancuernas = RoomExercise(0, "Step Up con mancuernas", Date(), piernaId)
        daoExercise.insertExercise(stepUpMancuernas)

        val caminataLateralMinibanda = RoomExercise(0, "Caminata lateral con minibanda", Date(), piernaId)
        daoExercise.insertExercise(caminataLateralMinibanda)

        val elevacionesRodilla = RoomExercise(0, "Elevaciones de rodilla", Date(), piernaId)
        daoExercise.insertExercise(elevacionesRodilla)

        val columpiosKettlebell = RoomExercise(0, "Columpios con kettlebell", Date(), piernaId)
        daoExercise.insertExercise(columpiosKettlebell)

        val contragolpeCable = RoomExercise(0, "Contragolpe con cable", Date(), piernaId)
        daoExercise.insertExercise(contragolpeCable)

        val patadasBurro = RoomExercise(0, "Patadas de burro", Date(), piernaId)
        daoExercise.insertExercise(patadasBurro)

        val elevacionesCaderaAcostadoLateralmente = RoomExercise(0, "Elevaciones de cadera acostado lateralmente", Date(), piernaId)
        daoExercise.insertExercise(elevacionesCaderaAcostadoLateralmente)

        val sentadillaPosturasFuncionales = RoomExercise(0, "Sentadilla Posturas funcionales", Date(), piernaId)
        daoExercise.insertExercise(sentadillaPosturasFuncionales)

    }

    private suspend fun espaldaExercises(espaldaId: Int) {
        // Espalda exercises
        val remoMancuernaUnaMano = RoomExercise(0, "Remo con mancuerna a una mano", Date(), espaldaId)
        daoExercise.insertExercise(remoMancuernaUnaMano)

        val jalonAgarreAncho = RoomExercise(0, "Jalón con agarre ancho", Date(), espaldaId)
        daoExercise.insertExercise(jalonAgarreAncho)

        val remoMaquina = RoomExercise(0, "Remo en máquina", Date(), espaldaId)
        daoExercise.insertExercise(remoMaquina)

        val jalonPechoAgarreCerrado = RoomExercise(0, "Jalón al pecho con agarre cerrado", Date(), espaldaId)
        daoExercise.insertExercise(jalonPechoAgarreCerrado)

        val remoBarra = RoomExercise(0, "Remo con barra", Date(), espaldaId)
        daoExercise.insertExercise(remoBarra)

        val jalonTrasNuca = RoomExercise(0, "Jalón tras nuca", Date(), espaldaId)
        daoExercise.insertExercise(jalonTrasNuca)

        val jalonPechoAgarreInvertido = RoomExercise(0, "Jalón al pecho con agarre invertido", Date(), espaldaId)
        daoExercise.insertExercise(jalonPechoAgarreInvertido)

        val jalonPoleaCuerda = RoomExercise(0, "Jalón en polea con cuerda", Date(), espaldaId)
        daoExercise.insertExercise(jalonPoleaCuerda)

        val remoBarraT = RoomExercise(0, "Remo en barra T", Date(), espaldaId)
        daoExercise.insertExercise(remoBarraT)

        val remoInclinadoBarraSupinado = RoomExercise(0, "Remo inclinado con barra con agarre supinado", Date(), espaldaId)
        daoExercise.insertExercise(remoInclinadoBarraSupinado)

        val elevacionesBarraFija = RoomExercise(0, "Elevaciones en barra fija", Date(), espaldaId)
        daoExercise.insertExercise(elevacionesBarraFija)

        val elevacionesNucaBarraFija = RoomExercise(0, "Elevaciones tras nuca en barra fija", Date(), espaldaId)
        daoExercise.insertExercise(elevacionesNucaBarraFija)

        val elevacionesBarraFijaSupinado = RoomExercise(0, "Elevaciones en barra fija con agarre supinado", Date(), espaldaId)
        daoExercise.insertExercise(elevacionesBarraFijaSupinado)

        val jalonDorsalBrazosRectos = RoomExercise(0, "Jalón dorsal con brazos rectos", Date(), espaldaId)
        daoExercise.insertExercise(jalonDorsalBrazosRectos)

        val remoInclinadoMancuernas = RoomExercise(0, "Remo inclinado con mancuernas", Date(), espaldaId)
        daoExercise.insertExercise(remoInclinadoMancuernas)

        val pulloverMancuerna = RoomExercise(0, "Pullover con mancuerna", Date(), espaldaId)
        daoExercise.insertExercise(pulloverMancuerna)

        val pulloverBarra = RoomExercise(0, "Pullover con barra", Date(), espaldaId)
        daoExercise.insertExercise(pulloverBarra)

        val pesoMuertoBarra = RoomExercise(0, "Peso muerto con barra", Date(), espaldaId)
        daoExercise.insertExercise(pesoMuertoBarra)

        val pesoMuertoSumoBarra = RoomExercise(0, "Peso muerto sumo con barra", Date(), espaldaId)
        daoExercise.insertExercise(pesoMuertoSumoBarra)

        val pesoMuertoBarraHexagonal = RoomExercise(0, "Peso muerto con barra hexagonal", Date(), espaldaId)
        daoExercise.insertExercise(pesoMuertoBarraHexagonal)

        val pesoMuertoMancuernas = RoomExercise(0, "Peso muerto con mancuernas", Date(), espaldaId)
        daoExercise.insertExercise(pesoMuertoMancuernas)

        val encogimientoHombrosBarra = RoomExercise(0, "Encogimiento de hombros con barra", Date(), espaldaId)
        daoExercise.insertExercise(encogimientoHombrosBarra)

        val encogimientoHombrosMancuernas = RoomExercise(0, "Encogimiento de hombros con mancuernas", Date(), espaldaId)
        daoExercise.insertExercise(encogimientoHombrosMancuernas)
    }

    private suspend fun gemelosExercises(gemelosId: Int) {
        // Gemelos exercises
        val elevacionGemelosSentado = RoomExercise(0, "Elevación de gemelos sentado", Date(), gemelosId)
        daoExercise.insertExercise(elevacionGemelosSentado)

        val elevacionGemelosPie = RoomExercise(0, "Elevación de gemelos de pie", Date(), gemelosId)
        daoExercise.insertExercise(elevacionGemelosPie)

        val elevacionMultipower = RoomExercise(0, "Elevacion multipower", Date(), gemelosId)
        daoExercise.insertExercise(elevacionMultipower)

        val elevacionPrensa = RoomExercise(0, "Elevacion prensa", Date(), gemelosId)
        daoExercise.insertExercise(elevacionPrensa)

    }

    private suspend fun tricepsExercises(tricepsId: Int) {
        // Triceps exercises
        val extensionTricepsTumbado = RoomExercise(0, "Extensión de tríceps tumbado", Date(), tricepsId)
        daoExercise.insertExercise(extensionTricepsTumbado)

        val extensionTricepsPolea = RoomExercise(0, "Extensión de tríceps en polea", Date(), tricepsId)
        daoExercise.insertExercise(extensionTricepsPolea)

        val extensionTricepsPoleaCuerda = RoomExercise(0, "Extensión de tríceps en polea con cuerda", Date(), tricepsId)
        daoExercise.insertExercise(extensionTricepsPoleaCuerda)

        val extensionTricepsMancuernasCabeza = RoomExercise(0, "Extensión de tríceps con mancuernas por encima de la cabeza", Date(), tricepsId)
        daoExercise.insertExercise(extensionTricepsMancuernasCabeza)

        val pressBancaAgarreCerrado = RoomExercise(0, "Press Banca con agarre cerrado", Date(), tricepsId)
        daoExercise.insertExercise(pressBancaAgarreCerrado)

        val patadasTraseras = RoomExercise(0, "Patadas traseras", Date(), tricepsId)
        daoExercise.insertExercise(patadasTraseras)

        val extensionTricepsCableAgarreInverso = RoomExercise(0, "Extensión de tríceps con cable de agarre inverso con barra", Date(), tricepsId)
        daoExercise.insertExercise(extensionTricepsCableAgarreInverso)

        val extensionTricepsCableUnaMano = RoomExercise(0, "Extensión de tríceps con cable a una mano", Date(), tricepsId)
        daoExercise.insertExercise(extensionTricepsCableUnaMano)

        val extensionTricepsCableUnaManoSupinado = RoomExercise(0, "Extensión de tríceps con cable a una mano con agarre supinado", Date(), tricepsId)
        daoExercise.insertExercise(extensionTricepsCableUnaManoSupinado)

        val extensionTricepsMancuernasTumbado = RoomExercise(0, "Extensión de tríceps con mancuernas tumbado", Date(), tricepsId)
        daoExercise.insertExercise(extensionTricepsMancuernasTumbado)

        val pressFrancesSentado = RoomExercise(0, "Press francés sentado con barra", Date(), tricepsId)
        daoExercise.insertExercise(pressFrancesSentado)

        val fondosBanco = RoomExercise(0, "Fondos en banco", Date(), tricepsId)
        daoExercise.insertExercise(fondosBanco)

        val fondosBarrasParalelas = RoomExercise(0, "Fondos en barras paralelas", Date(), tricepsId)
        daoExercise.insertExercise(fondosBarrasParalelas)
    }

    private suspend fun pectoralExercises(pectoralId: Int) {
        // Pectoral exercises
        val pressBancaConBarra = RoomExercise(0, "Press de banca con barra", Date(), pectoralId)
        daoExercise.insertExercise(pressBancaConBarra)

        val pressBancaInclinadoMancuernas = RoomExercise(0, "Press banca inclinado con mancuernas", Date(), pectoralId)
        daoExercise.insertExercise(pressBancaInclinadoMancuernas)

        val aperturasPeckDeck = RoomExercise(0, "Aperturas en máquina Peck Deck o Contractora", Date(), pectoralId)
        daoExercise.insertExercise(aperturasPeckDeck)

        val crucePoleas = RoomExercise(0, "Cruce de poleas", Date(), pectoralId)
        daoExercise.insertExercise(crucePoleas)

        val pressBancaInclinadoBarra = RoomExercise(0, "Press de banca inclinado con barra", Date(), pectoralId)
        daoExercise.insertExercise(pressBancaInclinadoBarra)

        val pressBancaMancuernas = RoomExercise(0, "Press de banca con mancuernas", Date(), pectoralId)
        daoExercise.insertExercise(pressBancaMancuernas)

        val aperturasMancuernas = RoomExercise(0, "Aperturas con mancuernas", Date(), pectoralId)
        daoExercise.insertExercise(aperturasMancuernas)

        val aperturasInclinadasMancuernas = RoomExercise(0, "Aperturas Inclinadas con mancuernas", Date(), pectoralId)
        daoExercise.insertExercise(aperturasInclinadasMancuernas)

        val pressBancaMaquinaSentado = RoomExercise(0, "Press de banca en máquina sentado", Date(), pectoralId)
        daoExercise.insertExercise(pressBancaMaquinaSentado)

        val pressBancaDeclinadoBarra = RoomExercise(0, "Press de banca declinado con barra", Date(), pectoralId)
        daoExercise.insertExercise(pressBancaDeclinadoBarra)

        val pressBancaDeclinadoMancuernas = RoomExercise(0, "Press de banca declinado con mancuernas", Date(), pectoralId)
        daoExercise.insertExercise(pressBancaDeclinadoMancuernas)

        val flexiones = RoomExercise(0, "Flexiones", Date(), pectoralId)
        daoExercise.insertExercise(flexiones)
    }

}