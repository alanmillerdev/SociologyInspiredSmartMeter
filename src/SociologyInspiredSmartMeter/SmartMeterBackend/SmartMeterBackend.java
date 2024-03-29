package SociologyInspiredSmartMeter.SmartMeterBackend;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import SociologyInspiredSmartMeter.Controller;
import SociologyInspiredSmartMeter.SmartMeterBackend.parameters.UserParameters;
import SociologyInspiredSmartMeter.SmartMeterClient.Settings;
import SociologyInspiredSmartMeter.SmartMeterClient.Config;

public class SmartMeterBackend extends UserParameters {

    // Create a single Random object for generating random numerical data for the simulation, a single object exists to
    // allow for result replication given a specific user seed.
    static Random random = new Random();

     // Create a new controller object to store the passed controller object.
    static Controller controller;

    // Create a new config object to store the passed config object.
    Config config;

    // Create a new settings object to store the passed settings object.
    Settings settings;

    public static String dataOutputFolder;

    public void smartMeterSimulationRun(Controller passedController, Config passedConfig, Settings passedSettings)
    {
        // Store the passed controller object.
        controller = passedController;

        // Store the passed config object. 
        config = passedConfig;

        // Store the passed settings object.
        settings = passedSettings;

        try {
            Thread.sleep(5000);
            headlessSimulation();
        } catch (IOException ioe)
        {

        }catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * This is the headlessSimulation method which runs a headless version of the ResourceExchangeArena simulation.
     * This can be used to run simulations to get results to compare with the GUI version.
     *
     * @param args Unused.
     * @exception IOException On input error.
     * @see IOException
     */
    public static void headlessSimulation() throws IOException
    {

        switch (COMPARISON_LEVEL) {
            case 1:
                // Test user parameters with and without social capital for comparison.
                USE_SOCIAL_CAPITAL = false;
                runHeadlessSimulationSet();
                System.out.println("********** 1 / 2 ENVIRONMENT VERSIONS COMPLETE **********");

                USE_SOCIAL_CAPITAL = true;
                runHeadlessSimulationSet();
                System.out.println("********** 2 / 2 ENVIRONMENT VERSIONS COMPLETE **********");
                break;
            case 2:
                // As above but also test single agent type populations for reference.
                USE_SOCIAL_CAPITAL = false;
                SINGLE_AGENT_TYPE = true;
                SELECTED_SINGLE_AGENT_TYPE = SELFISH;
                runHeadlessSimulationSet();
                System.out.println("********** 1 / 5 ENVIRONMENT VERSIONS COMPLETE **********");

                USE_SOCIAL_CAPITAL = false;
                SINGLE_AGENT_TYPE = true;
                SELECTED_SINGLE_AGENT_TYPE = SOCIAL;
                runHeadlessSimulationSet();
                System.out.println("********** 2 / 5 ENVIRONMENT VERSIONS COMPLETE **********");

                USE_SOCIAL_CAPITAL = true;
                SINGLE_AGENT_TYPE = true;
                SELECTED_SINGLE_AGENT_TYPE = SOCIAL;
                runHeadlessSimulationSet();
                System.out.println("********** 3 / 5 ENVIRONMENT VERSIONS COMPLETE **********");

                USE_SOCIAL_CAPITAL = false;
                SINGLE_AGENT_TYPE = false;
                runHeadlessSimulationSet();
                System.out.println("********** 4 / 5 ENVIRONMENT VERSIONS COMPLETE **********");

                USE_SOCIAL_CAPITAL = true;
                SINGLE_AGENT_TYPE = false;
                runHeadlessSimulationSet();
                System.out.println("********** 5 / 5 ENVIRONMENT VERSIONS COMPLETE **********");
                break;
            default:
                // Run only the set of parameters defined by the user.
                runHeadlessSimulationSet();
        }

        // String version of starting ratios for file names.
        ArrayList<String> startingRatiosArray = new ArrayList<String>();

        for (int[] AGENT_TYPES : AGENT_TYPES_ARRAY) {
            String ratio = "";
            int typesListed = 0;
            for (int type : AGENT_TYPES) {
                if (typesListed != 0) {
                    ratio += "_";
                }
                typesListed++;
                ratio += Inflect.getHumanReadableAgentType(type);
            }
            startingRatiosArray.add(ratio);          
        }
    }

    private static void runHeadlessSimulationSet() throws IOException {
        // Set the simulations initial random seed.
        random.setSeed(seed);

        // Create a directory to store the data output by all simulations being run.
        dataOutputFolder = FOLDER_NAME + "/useSC_" + USE_SOCIAL_CAPITAL + "_AType_";
        if (!SINGLE_AGENT_TYPE) {
            dataOutputFolder += "mixed";
        } else {
            dataOutputFolder += Inflect.getHumanReadableAgentType(SELECTED_SINGLE_AGENT_TYPE);
        }

        Path dataOutputPath = Paths.get(dataOutputFolder);
        Files.createDirectories(dataOutputPath);

        // Stores the key data about all simulations for organisational purposes.
        File allSimulationsData = new File(dataOutputFolder, "allSimulationsData.txt");

        FileWriter allSimulationsDataWriter = new FileWriter(allSimulationsData);

        allSimulationsDataWriter.append("Simulation Information (all runs): \n\n");
        allSimulationsDataWriter.append("Single agent type: ").append(String.valueOf(SINGLE_AGENT_TYPE)).append("\n");
        if (SINGLE_AGENT_TYPE) {
            allSimulationsDataWriter.append("Agent type: ")
                    .append(String.valueOf(SELECTED_SINGLE_AGENT_TYPE)).append("\n");
        }
        allSimulationsDataWriter.append("Use social capital: ").append(String.valueOf(USE_SOCIAL_CAPITAL)).append("\n");
        allSimulationsDataWriter.append("Simulation runs: ").append(String.valueOf(SIMULATION_RUNS)).append("\n");
        allSimulationsDataWriter.append("Additional Days: ").append(String.valueOf(DAYS)).append("\n");
        allSimulationsDataWriter.append("Population size: ").append(String.valueOf(POPULATION_SIZE)).append("\n");
        allSimulationsDataWriter.append("Unique time slots: ").append(String.valueOf(UNIQUE_TIME_SLOTS)).append("\n");
        allSimulationsDataWriter.append("Slots per agent: ").append(String.valueOf(SLOTS_PER_AGENT)).append("\n");
        allSimulationsDataWriter.append("Simulation Information (specific run details): \n\n");


        // Percentage of learning agents converted to actual number of agents that can learn each day.
        int[] numberOfLearningAgents = new int[PERCENTAGE_OF_AGENTS_TO_EVOLVE_ARRAY.length];
        float onePercent = POPULATION_SIZE / 100.0f;
        for (int i = 0; i < PERCENTAGE_OF_AGENTS_TO_EVOLVE_ARRAY.length; i++) {
            int learningAgents = Math.round(onePercent * PERCENTAGE_OF_AGENTS_TO_EVOLVE_ARRAY[i]);
            numberOfLearningAgents[i] = learningAgents;
        }

        // Perform a parameter sweep for the key parameters being tested.
        int simVersionsCompleted = 0;
        parameterSweep:
        for (int[] AGENT_TYPES : AGENT_TYPES_ARRAY) {
            for (int i = 0; i < numberOfLearningAgents.length; i++) {

                String fileName;

                if (!SINGLE_AGENT_TYPE) {
                    fileName = "AE_" + PERCENTAGE_OF_AGENTS_TO_EVOLVE_ARRAY[i];

                    StringBuilder typeRatio = new StringBuilder("_SR_");
                    int typesListed = 0;
                    for (int type : AGENT_TYPES) {
                        if (typesListed != 0) {
                            typeRatio.append("_");
                        }
                        typesListed++;
                        typeRatio.append(Inflect.getHumanReadableAgentType(type));
                    }
                    fileName += typeRatio;
                } else {
                    fileName = "SR_" + Inflect.getHumanReadableAgentType(SELECTED_SINGLE_AGENT_TYPE);
                }

                String initialSeed = seed + "L";

                // The parameters about to be tested are stored so that it is clear what they were when looking at
                // the results.
                allSimulationsDataWriter.append("Seed: ").append(initialSeed).append("\n");
                allSimulationsDataWriter.append("Number of agents to evolve: ")
                        .append(String.valueOf(numberOfLearningAgents[i]))
                        .append("\n");
                if (!SINGLE_AGENT_TYPE) {
                    allSimulationsDataWriter.append("Starting ratio of agent types: ");
                    int typesListed = 0;
                    for (int type : AGENT_TYPES) {
                        if (typesListed != 0) {
                            allSimulationsDataWriter.append(" : ");
                        }
                        typesListed++;
                        allSimulationsDataWriter.append(Inflect.getHumanReadableAgentType(type));
                    }
                }
                allSimulationsDataWriter.append("\n\n");

                /*
                 * The arena is the environment in which all simulations take place.
                 *
                 * @param folderName String representing the output destination folder, used to organise output
                 *                   data.
                 * @param environmentTag String detailing specifics about the simulation environment.
                 * @param demandCurves Double arrays of demand used by the agents, when multiple curves are used
                 *                     the agents are split equally between the curves.
                 * @param availabilityCurve Integer array of energy availability used by the simulation.
                 * @param socialCapital Boolean value that determines whether or not social agents will utilise
                 *                      social capital.
                 * @param simulationRuns Integer value representing the number of simulations to be ran and
                 *                       averaged.
                 * @param days Integer value representing the number of days to be simulated.
                 * @param populationSize Integer value representing the size of the initial agent population.
                 * @param uniqueTimeSlots Integer value representing the number of unique time slots available in
                 *                        the simulation.
                 * @param slotsPerAgent Integer value representing the number of time slots each agent requires.
                 * @param numberOfAgentsToEvolve Integer value representing the number of Agents who's strategy
                 *                               will change at the end of each day.
                 * @param agentTypes Integer array containing the agent types that the simulation will begin with.
                 *                   The same type can exist multiple times in the array where more agents of one
                 *                   type are required.
                 * @param singleAgentType Boolean value specifying whether only a single agent type should exist,
                 *                        used for establishing baseline results.
                 * @param selectedSingleAgentType Integer value representing the single agent type to be modelled
                 *                                when singleAgentType is true.
                 * @param pythonExe String representing the system path to python environment executable.
                 * @param pythonPath String representing the system path to the python data visualiser.
                 * @exception IOException On input error.
                 * @see IOException
                 */
                new ArenaEnvironment(
                        dataOutputFolder,
                        fileName,
                        DEMAND_CURVES,
                        AVAILABILITY_CURVE,
                        USE_SOCIAL_CAPITAL,
                        SIMULATION_RUNS,
                        DAYS,
                        POPULATION_SIZE,
                        UNIQUE_TIME_SLOTS,
                        SLOTS_PER_AGENT,
                        numberOfLearningAgents[i],
                        AGENT_TYPES,
                        SINGLE_AGENT_TYPE,
                        SELECTED_SINGLE_AGENT_TYPE,
                        controller
                );

                simVersionsCompleted++;
                System.out.println("Simulation versions completed: " + simVersionsCompleted);

                if (SINGLE_AGENT_TYPE) {
                    break parameterSweep;
                }
            }
        }
        allSimulationsDataWriter.close();
    }
}
