package com.tenriver.kpopmultiplechoice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tenriver.kpopmultiplechoice.QuizContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyKpopQuizMultipleChoice.db";
    private static final int DATABASE_VERSION = 1; // 데이터베이스 버전 항상 다르게 하기

    private SQLiteDatabase db;

    public QuizDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        // 주의 깊게 코딩
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                // QuizContract에 있는 부분
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_SINGER + " TEXT, " +
                QuestionsTable.COLUMN_YEAR + " INTEGER " +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);

    }

    private void fillQuestionsTable() {
        Question q1 = new Question("WYy2fROj7uU", "어디에도 (No matter where)", "사랑이 식었다고 말해도 돼 (My love has faded away) ", "처음처럼 (BLOOM) ", "그대라는 사치 (Amazing You) ",4,"Han Dong Geun(한동근)",2016);	addQuestion(q1);
        Question q2 = new Question("kCSFdRf0210", "내가 설렐 수 있게 (Only one)", "NoNoNo ", "너 그리고 나 (NAVILLERA) ", "1도 없어 (I'm so sick) ",1,"Apink(에이핑크)",2016);	addQuestion(q2);
        Question q3 = new Question("L-2M_-QLs8k", "이별하러 가는 길 (The Way To Say Goodbye)", "내가 저지른 사랑(The love I committed) ", "하루도 그대를 사랑하지 않은 적이 없었다(There has never been a day I haven't loved you) ", "이 노래가 뭐라고 (This Song) ",2,"임창정",2016);	addQuestion(q3);
        Question q4 = new Question("bstWoRsocaw", "그냥 안아달란 말야 (Just hug me)", "너에게 못했던 내 마지막 말은 (Unspoken Words) ", "내 옆에 그대인걸 (Beside me) ", "하늘바라기 (Hopefully sky) ",3,"다비치 (DAVICHI)",2016);	addQuestion(q4);
        Question q5 = new Question("1ZhDsPdvl6c", "내가 설렐 수 있게 (Only one)", "NoNoNo ", "너 그리고 나 (NAVILLERA) ", "1도 없어 (I'm so sick) ",3,"GFRIEND(여자친구)",2016);	addQuestion(q5);
        Question q6 = new Question("xbf2c0JBJic", "Artist", "BERMUDA TRIANGLE", "사랑이었다 (Feat. LUNA of f(x)) ", "너는 나 나는 너 (I am you, you are me) ",4,"지코 (ZICO)",2016);	addQuestion(q6);
        Question q7 = new Question("GdxvD7r58ng", "너무너무너무 (Very Very Very)", "Crush ", "소나기 (DOWNPOUR) ", "Whatta Man (Good man) ",1,"아이오아이 (I.O.I)",2016);	addQuestion(q7);
        Question q8 = new Question("QbhmTM1cRro", "넌 is 뭔들 (You're the best)", "데칼코마니 (Décalcomanie)  ", "나로 말할 것 같으면 (Yes I am) ", "아재개그 (AZE GAG) ",1,"마마무 (MAMAMOO)",2016);	addQuestion(q8);
        Question q9 = new Question("MfYPKZl7W1w", "서울 밤 (Seoul Night)", "널 사랑하지 않아 (I Don't Love You) ", "소원 (Wish) ", "달고나 (Sugar and Me) ",2,"어반자카파(Urban Zakapa)",2016);	addQuestion(q9);
        Question q10 = new Question("y2OFPvYxZuY", "넌 is 뭔들 (You're the best)", "데칼코마니 (Décalcomanie)  ", "나로 말할 것 같으면 (Yes I am) ", "아재개그 (AZE GAG) ",2,"마마무 (MAMAMOO)",2016);	addQuestion(q10);
        Question q11 = new Question("WfYgbFBFe1E", "Bambi", "UN Village ", "비가와 (Rain) ", "Dream ",4,"수지(Suzy), 백현(BAEKHYUN)",2016);	addQuestion(q11);
        Question q12 = new Question("eelfrHtmk68", "D (Half Moon)", "instagram ", "나빠 (NAPPA) ", "둘만의 세상으로 가 (Let Us Go) ",1,"DEAN",2016);	addQuestion(q12);
        Question q13 = new Question("QslJYDX3o8s", "러시안 룰렛 (Russian Roulette)", "7월 7일 (One Of These Nights) ", "Rookie ", "Would U ",1,"Red Velvet (레드벨벳)",2016);	addQuestion(q13);
        Question q14 = new Question("eHir_vB1RUI", "Rain", "Starlight (Feat. DEAN) ", "Why ", "This Christmas ",1,"TAEYEON (태연)",2016);	addQuestion(q14);
        Question q15 = new Question("ST8O-AeY3Uo", "RE-BYE", "DINOSAUR", "선물을 고르며 (A Gift!)", "낙하 (NAKKA) (with IU)",1,"AKMU",2016);	addQuestion(q15);
        Question q16 = new Question("cIGgSI1uhKI", "봄이 좋냐?? (What The Spring??)", "폰서트 (Phonecert)", "당신과는 천천히 (every moment with you)", "그러나",1,"10cm",2016);	addQuestion(q16);
        Question q17 = new Question("erErBFKPbMY", "솔직히 (Honestly…)", "봄인가 봐 (Spring Love) ", "유후 (You, Who?) ", "가을 타나 봐 (Fall in Fall) ",2,"에릭남 X 웬디",2016);	addQuestion(q17);
        Question q18 = new Question("9pdj4iJD08s", "불장난 (Playing With Fire)", "Lovesick Girls ", "LALISA ", "마지막처럼 (AS IF IT'S YOUR LAST) ",1,"BLACKPINK",2016);	addQuestion(q18);
        Question q19 = new Question("4ujQOR2DMFM", "Come Back Home", "DNA ", "피 땀 눈물 (Blood Sweat & Tears) ", "불타오르네 (FIRE) ",4,"BTS (방탄소년단)",2016);	addQuestion(q19);
        Question q20 = new Question("bwmSjveL3Lc", "불장난 (Playing With Fire)", "붐바야 (BOOMBAYAH) ", "휘파람 (WHISTLE) ", "마지막처럼 (AS IF IT'S YOUR LAST) ",2,"BLACKPINK",2016);	addQuestion(q20);
        Question q21 = new Question("h9mvVTgfKCs", "Artist", "BERMUDA TRIANGLE", "사랑이었다 (Feat. LUNA of f(x)) ", "너는 나 나는 너 (I am you, you are me) ",3,"지코 (ZICO)",2016);	addQuestion(q21);
        Question q22 = new Question("OV9NJGTLm-4", "센치해(SENTIMENTAL)", "BABY BABY ", "좋더라 (I'M YOUNG) ", "LOVE ME LOVE ME ",1,"WINNER",2016);	addQuestion(q22);
        Question q23 = new Question("gluvBLJYBjs", "DON'T TOUCH ME", "Shut Up ", "다시 여기 바닷가(Beach Again) ", "몰랐니 (Lil' Touch)",2,"Unnies(언니쓰)",2016);	addQuestion(q23);
        Question q24 = new Question("0VKcLPdY9lI", "시간을 달려서 (Rough)", "너 그리고 나 (NAVILLERA) ", "여름비 (Summer Rain) ", "밤 (Time for the moon night) ",1,"GFRIEND(여자친구)",2016);	addQuestion(q24);
        Question q25 = new Question("LJVUjmNMF8c", "Got That Boom", "I Like That ", "Who Dis? ", "LONELY ",2,"씨스타(SISTAR)",2016);	addQuestion(q25);
        Question q26 = new Question("J-wFp43XOrA", "아주 NICE (VERY NICE)", "CLAP ", "울고 싶지 않아 (Don't Wanna Cry) ", "Rock with you ",1,"SEVENTEEN (세븐틴)",2016);	addQuestion(q26);
        Question q27 = new Question("va5rf20Un24", "어디에도 (No matter where)", "사랑이 식었다고 말해도 돼 (My love has faded away) ", "처음처럼 (BLOOM) ", "그대라는 사치 (Amazing You) ",1,"M.C THE MAX(엠씨더맥스)",2016);	addQuestion(q27);
        Question q28 = new Question("iIPH8LFYFRk", "LAST DANCE", "센치해(SENTIMENTAL) ", "에라 모르겠다 (FXXK IT) ", "아예 (AH YEAH) ",3,"BIGBANG",2016);	addQuestion(q28);
        Question q29 = new Question("RnDSODY7bVE", "낮보다는 밤 (Night Rather Than Day)", "L.I.E 엘라이 ", "멍청이 (TWIT) ", "덜덜덜(DDD) ",2,"EXID(이엑스아이디)",2016);	addQuestion(q29);
        Question q30 = new Question("PYGODWJgR-c", "가시나 (Gashina)", "꼬리(TAIL) ", "보라빛 밤 (pporappippam) ", "Why So Lonely ",4,"Wonder Girls(원더걸스)",2016);	addQuestion(q30);
        Question q31 = new Question("9U8uA702xrE", "싸운날", "나만 안되는 연애(Hard To Love) ", "좋다고 말해(Tell me you like me) ", "쏘쏘(SO-SO)",4,"BOL4(볼빨간사춘기)",2016);	addQuestion(q31);
        Question q32 = new Question("_3tIkwvUjJg", "If You", "Is You", "All With You", "그대라는 시 (All about you)",1,"Ailee(에일리)",2016);	addQuestion(q32);
        Question q33 = new Question("TcytstV1_XE", "BERMUDA TRIANGLE", "Beautiful ", "어떻게 지내 (fall) ", "잊어버리지마 (Don’t Forget) ",4,"Crush(크러쉬)",2016);	addQuestion(q33);
        Question q34 = new Question("y5MAgMVwfFs", "싸운날", "나만 안되는 연애(Hard To Love) ", "좋다고 말해(Tell me you like me) ", "피카부 (Peek-A-Boo)",3,"BOL4(볼빨간사춘기)",2016);	addQuestion(q34);
        Question q35 = new Question("c7rCyll5AeY", "Merry & Happy", "SIGNAL ", "TT (티티) ", "CHEER UP ",4,"TWICE(트와이스)",2016);	addQuestion(q35);
        Question q36 = new Question("ePpPVE-GGJw", "Merry & Happy", "SIGNAL ", "TT (티티) ", "CHEER UP ",3,"TWICE(트와이스)",2016);	addQuestion(q36);
        Question q37 = new Question("hmE9f-TEutc", "Come Back Home", "DNA ", "피 땀 눈물 (Blood Sweat & Tears) ", "불타오르네 (FIRE) ",3,"BTS (방탄소년단)",2016);	addQuestion(q37);
        Question q38 = new Question("5iSlfF8TQ9k", "소리 (Sori)", "한숨 (BREATHE) ", "누구 없소 (NO ONE) ", "홀로 (HOLO) ",2,"LEE HI",2016);	addQuestion(q38);
        Question q39 = new Question("dISNgvVpWlo", "불장난 (Playing With Fire)", "붐바야 (BOOMBAYAH) ", "휘파람 (WHISTLE) ", "마지막처럼 (AS IF IT'S YOUR LAST) ",3,"BLACKPINK",2016);	addQuestion(q39);
        Question q40 = new Question("JtjxZoa_LHM", "Toy", "DRAMARAMA ", "Shall We Dance ", "YESTERDAY ",1,"블락비 (Block B)",2016);	addQuestion(q40);
        Question q41 = new Question("nzDO6tAB6ng", "그냥 안아달란 말야 (Just hug me)", "너에게 못했던 내 마지막 말은 (Unspoken Words) ", "내 옆에 그대인걸 (Beside me) ", "하늘바라기 (Hopefully sky) ",4,"Jeong Eun Ji(정은지)",2016);	addQuestion(q41);
        Question q42 = new Question("MOuzcBERbsc", "별 (Star) (Little Prince)", "주지마 (Above Live) ", "GOOD (Feat. ELO) ", "시간이 들겠지 (It Takes Time) ",3,"로꼬, GRAY (그레이)",2016);	addQuestion(q42);
        Question q43 = new Question("8avNw5EWBYs", "서울 밤 (Seoul Night)", "널 사랑하지 않아 (I Don't Love You) ", "소원 (Wish) ", "달고나 (Sugar and Me) ",4,"San E, Raina(레이나)",2016);	addQuestion(q43);
        Question q44 = new Question("5JbVVlqrreE", "첫사랑 (first love)", "사랑하지 않은 것처럼 (The Love) ", "나비잠 (Sweet Dream) ", "십이월 이십오일의 고백 (My christmas wish) ",3,"희철 X 민경훈",2016);	addQuestion(q44);
        Question q45 = new Question("XUR8QByF2As", "저 별 (Star)", "And July (Feat. DEAN, DJ Friz) ", "Shut Up & Groove Feat.DEAN ", "널 너무 모르고 (Don't know you) ",1,"헤이즈 (Heize)",2016);	addQuestion(q45);
        Question q46 = new Question("ZHoLaLlL5lA", "Artist", "BERMUDA TRIANGLE", "사랑이었다 (Feat. LUNA of f(x)) ", "너는 나 나는 너 (I am you, you are me) ",2,"지코 (ZICO)",2016);	addQuestion(q46);
        Question q47 = new Question("tr6Xi0DNWj8", "IndiGO", "띵 (DDING) ", "METEOR ", "또 하루 (Lonely Night)(feat. GAEKO(개코)) ",4,"GARY(개리)",2016);	addQuestion(q47);
        Question q48 = new Question("oBKpJiVEcnU", "답장 (Reply)", "숨(Breath) ", "굿바이 (Goodbye) ", "기억의 빈자리 (Emptiness in Memory) ",2,"박효신(Park Hyo Shin)",2016);	addQuestion(q48);
        Question q49 = new Question("XsOGiTSZ_cg", "BAD LOVE", "WANT ", "MOVE ", "Press Your Number ",4,"TAEMIN (태민)",2016);	addQuestion(q49);
        Question q50 = new Question("9xWiro_tS1k", "러시안 룰렛 (Russian Roulette)", "7월 7일 (One Of These Nights) ", "Rookie ", "Would U ",2,"Red Velvet (레드벨벳)",2016);	addQuestion(q50);
        Question q51 = new Question("vcnv3jjWgSc", "봄날의 기억(Remember that)", "몇 년 후에 (A Few Years Later) ", "MOVIE ", "쏘쏘(SO-SO)",1,"BTOB(비투비)",2016);	addQuestion(q51);
        Question q52 = new Question("Q4vFviZ4qw0", "Fly", "하드캐리(Hard Carry) ", "Never Ever ", "Look ",1,"GOT7(갓세븐)",2016);	addQuestion(q52);
        Question q53 = new Question("qqqRn0l0PKE", "봄날의 기억(Remember that)", "몇 년 후에 (A Few Years Later) ", "MOVIE ", "있어 희미하게 (Just us 2)",2,"BTOB(비투비)",2016);	addQuestion(q53);
        Question q54 = new Question("eNmL4JiGxZQ", "Rain", "Starlight (Feat. DEAN) ", "Why ", "This Christmas ",2,"TAEYEON (태연)",2016);	addQuestion(q54);
        Question q55 = new Question("tbe3pe2BtwA", "Love Shot", "Monster ", "For Life ", "Lotto ",4,"EXO (엑소)",2016);	addQuestion(q55);
        Question q56 = new Question("y882AFjrSOM", "멍청이 (TWIT)", "Lip & Hip ", "베베(BABE) ", "어때? (How's this?) ",4,"HyunA(현아)",2016);	addQuestion(q56);
        Question q57 = new Question("pcKR0LPwoYs", "밤하늘의 저 별처럼 (Midnight)", "가끔 이러다 (Sometimes) ", "Stay With Me ", "Ring Ring ",3,"찬열, 펀치",2016);	addQuestion(q57);
        Question q58 = new Question("O57jr1oZDIw", "Fly", "하드캐리(Hard Carry) ", "Never Ever ", "Look ",2,"GOT7(갓세븐)",2016);	addQuestion(q58);
        Question q59 = new Question("WJua7KEP_oE", "Atlantis", "1 of 1 ", "Don't Call Me ", "Lo Siento (Feat. Leslie Grace) ",2,"SHINee (샤이니)",2016);	addQuestion(q59);
        Question q60 = new Question("DTiZ0aCm3rs", "Love Shot", "Monster ", "For Life ", "Lotto ",3,"EXO (엑소)",2016);	addQuestion(q60);
        Question q61 = new Question("AL2E1JDw2cA", "Shall We Dance", "Toy ", "자격지심 (Inferiority Complex) ", "YESTERDAY ",3,"박경 (Park Kyung)",2016);	addQuestion(q61);
        Question q62 = new Question("JwdmM8XY-nk", "겨울동화 (Winter Story)", "YOU AND I ", " 휘휘 (Hwi hwi) ", "BEcause ",1,"LABOUM(라붐)",2016);	addQuestion(q62);
        Question q63 = new Question("S_IBk0RCsOo", "Destiny (나의 지구)", "종소리 (Twinkle) ", "지금, 우리 (Now, We) ", "찾아가세요(Lost N Found) ",1,"러블리즈(Lovelyz)",2016);	addQuestion(q63);
        Question q64 = new Question("iLlLLBuuvVU", "싸운날", "나만 안되는 연애(Hard To Love) ", "좋다고 말해(Tell me you like me) ", "Listen to my word(내 얘길 들어봐)(A-ing)",2,"BOL4(볼빨간사춘기)",2016);	addQuestion(q64);
        Question q65 = new Question("1ri7I32Auhg", "싸운날", "나만 안되는 연애(Hard To Love) ", "좋다고 말해(Tell me you like me) ", "Hey - Mama!",1,"BOL4(볼빨간사춘기)",2016);	addQuestion(q65);
        Question q66 = new Question("LNZKqhXCv5c", "BUNGEE (Fall in Love)", "바나나 알러지 원숭이 (Banana allergy monkey) ", "LIAR LIAR ", "Listen to my word(내 얘길 들어봐)(A-ing)",3,"OH MY GIRL(오마이걸)",2016);	addQuestion(q66);
        Question q67 = new Question("hspqQuuuGIw", "내가 설렐 수 있게 (Only one)", "NoNoNo ", "너 그리고 나 (NAVILLERA) ", "1도 없어 (I'm so sick) ",2,"Apink (에이핑크)",2016);	addQuestion(q67);
        Question q68 = new Question("j59LLNMEOZk", "예쁘다 (Pretty U)", "붐붐 (BOOMBOOM) ", "아주 NICE (VERY NICE) ", "터치 (TOUCH) ",1,"세븐틴 (SEVENTEEN)",2016);	addQuestion(q68);
        Question q69 = new Question("IzplmS-VeBc", "예쁘다 (Pretty U)", "붐붐 (BOOMBOOM) ", "아주 NICE (VERY NICE) ", "터치 (TOUCH) ",2,"세븐틴 (SEVENTEEN)",2016);	addQuestion(q69);
        Question q70 = new Question("ONPbxm2C-RI", "BERMUDA TRIANGLE", "Cereal (Feat.ZICO) ", "우아해 (woo ah) ", "니가 하면 로맨스 (You call it romance) Feat. 다비치(Davichi) ",4,"케이윌(K.will)",2016);	addQuestion(q70);
        Question q71 = new Question("_W4dL2Av6LE", "BUNGEE (Fall in Love)", "바나나 알러지 원숭이 (Banana allergy monkey) ", "LIAR LIAR ", "Listen to my word(내 얘길 들어봐)(A-ing)",4,"OH MY GIRL(오마이걸)",2016);	addQuestion(q71);
        Question q72 = new Question("4EiNsoTc9kk", "Rose", "Dancing King ", "사랑, 하자 (Let’s Love) ", "괜찮아도 괜찮아 (That's okay) ",2,"유재석 X EXO",2016);	addQuestion(q72);
        Question q73 = new Question("6CM5iC_isug", "BERMUDA TRIANGLE", "Cereal (Feat.ZICO) ", "우아해 (woo ah) ", "니가 하면 로맨스 (You call it romance) Feat. 다비치(Davichi) ",3,"크러쉬 (Crush)",2016);	addQuestion(q73);
        Question q74 = new Question("rCeM57e2BfU", "저 별 (Star)", "And July (Feat. DEAN, DJ Friz) ", "Shut Up & Groove Feat.DEAN ", "널 너무 모르고 (Don't know you) ",2,"헤이즈 (Heize)",2016);	addQuestion(q74);
        Question q75 = new Question("WbhK3wMXluE", "Bye bye my blue", "니 소식 (Your regards) ", "다시 난, 여기 (Here I Am Again) ", "쏘쏘(SO-SO)",1,"백예린 (Yerin Baek)",2016);	addQuestion(q75);
        Question q76 = new Question("sno_genwMz8", "Good Luck (굿 럭)", "빙글뱅글 (Bingle Bangle) ", "Excuse Me ", "No ",1,"AOA",2016);	addQuestion(q76);
        Question q77 = new Question("jBBy2p5EQhs", "센치해(SENTIMENTAL)", "BABY BABY ", "좋더라 (I'M YOUNG) ", "LOVE ME LOVE ME ",2,"WINNER",2016);	addQuestion(q77);
        Question q78 = new Question("IlJHZJ8EqeA", "몸 (BODY)", "도망가 (Run away) ", "탕!♡ (TANG!♡) ", "아낙네 (FIANCÉ) ",1,"MINO",2016);	addQuestion(q78);
        Question q79 = new Question("P79G22cJe74", "센치해(SENTIMENTAL)", "BABY BABY ", "좋더라 (I'M YOUNG) ", "LOVE ME LOVE ME ",3,"WINNER",2016);	addQuestion(q79);
        Question q80 = new Question("JvjWy4saR08", "벚꽃연가 (Cherry Blossom Love Song)", "사월이 지나면 우리 헤어져요 (Beautiful goodbye) ", "너를 위해 (For You) ", "Hey - Mama!",3,"첸, 백현, 시우민 (EXO) CHEN, BAEKHYUN, XIUMIN (EXO)",2016);	addQuestion(q80);
        Question q81 = new Question("8Q2mth2bX10", "If You", "Is You ", "All With You ", "그대라는 시 (All about you) ",3,"태연 (TAEYEON)",2016);	addQuestion(q81);
        Question q82 = new Question("sk6WiGOmlso", "저 별 (Star)", "And July (Feat. DEAN, DJ Friz) ", "Shut Up & Groove Feat.DEAN ", "널 너무 모르고 (Don't know you) ",3,"헤이즈 (Heize)",2016);	addQuestion(q82);
        Question q83 = new Question("Kf3IumJmLqM", "너무너무너무 (Very Very Very)", "Crush ", "소나기 (DOWNPOUR) ", "Whatta Man (Good man) ",2,"아이오아이 (I.O.I)",2016);	addQuestion(q83);
        Question q84 = new Question("36HvpOE4opQ", "랑데부 (Rendezvous)", "Counting Stars (feat. Beenzino) ", "BAD LOVE ", "연애소설 (LOVE STORY) ",1,"Sik-K (식케이)",2016);	addQuestion(q84);
        Question q85 = new Question("sJB4er2pobw", "If You", "Is You ", "꿈 (Dream) ", "Dreams Come True",3,"김소현 (Kim Sohyun)",2016);	addQuestion(q85);
        Question q86 = new Question("wNxPGbk-gwA", "SHINE FOREVER", "Fighter ", "네게만 집착해 (Stuck) ", "걸어 (ALL IN) ",4,"MONSTA X (몬스타엑스)",2016);	addQuestion(q86);
        Question q87 = new Question("ro74Ki2jsNg", "SHINE FOREVER", "Fighter ", "네게만 집착해 (Stuck) ", "걸어 (ALL IN) ",3,"MONSTA X (몬스타엑스)",2016);	addQuestion(q87);
        Question q88 = new Question("3UGMDJ9kZCA", "일곱 번째 감각 (The 7th Sense)", "BOSS ", "Baby Don't Stop ", "Make A Wish (Birthday Song) ",1,"NCT U (엔시티 유)",2016);	addQuestion(q88);
        Question q89 = new Question("WkdtmT8A2iY", "Rain", "Starlight (Feat. DEAN) ", "Why ", "This Christmas ",3,"TAEYEON (태연)",2016);	addQuestion(q89);
        Question q90 = new Question("AOzVyVv1HtQ", "Love Shot", "Tempo ", "花요일 (Blooming Day) ", "Hey - Mama!",4,"EXO-CBX (첸백시)",2016);	addQuestion(q90);
        Question q91 = new Question("E0Y3ABqK7M4", "I Just Wanna Dance", "몰랐니 (Lil' Touch) ", "Holiday ", "I'll be yours ",1,"TIFFANY (티파니)",2016);	addQuestion(q91);
        Question q92 = new Question("5jTtU9VNALs", "SHINE FOREVER", "Fighter ", "네게만 집착해 (Stuck) ", "걸어 (ALL IN) ",2,"MONSTA X (몬스타엑스)",2016);	addQuestion(q92);
        Question q93 = new Question("_uJxJ7tSi1w", "BUTTERFLY", "Easy (이지) ", "HAPPY ", "비밀이야 (Secret) ",4,"우주소녀(WJSN)(COSMIC GIRLS)",2016);	addQuestion(q93);
        Question q94 = new Question("za3Uun9ToS4", "거짓말 (Lie) (Feat. 이해리 of 다비치)", "Lost Without You(우리집을 못 찾겠군요) (feat.Bolbbalgan4(볼빨간사춘기)) ", "보자보자 (Let's see) ", "Counting Stars (feat. Beenzino) ",1,"매드클라운 (Mad Clown)",2016);	addQuestion(q94);
        Question q95 = new Question("4bnIb1JJHdA", "Toy", "다이너마이트 (Dynamite) ", "Shall We Dance ", "YESTERDAY ",2,"VIXX (빅스)",2016);	addQuestion(q95);
        Question q96 = new Question("KSH-FVVtTf0", "Love Shot", "Monster ", "For Life ", "Lotto ",2,"EXO (엑소)",2016);	addQuestion(q96);
        Question q97 = new Question("BbQG-S4mU0U", "Bye bye my blue", "니 소식 (Your regards) ", "다시 난, 여기 (Here I Am Again) ", "쏘쏘(SO-SO)",4,"백아연",2016);	addQuestion(q97);
        Question q98 = new Question("FNnYIIdTBhQ", "BERMUDA TRIANGLE", "Beautiful ", "어떻게 지내 (fall) ", "잊어버리지마 (Don’t Forget) ",3,"Crush(크러쉬)",2016);	addQuestion(q98);
        Question q99 = new Question("wLPgdkLMCKU", "기다렸다 가 (nosedive)", "옥탑방 (Rooftop) ", "Pump It Up ", "Nostalgia ",1,"Dynamic Duo(다이나믹 듀오), CHEN(첸)",2017);	addQuestion(q99);
        Question q100 = new Question("0wlXaHmmOVc", "답장 (Reply)", "숨(Breath) ", "굿바이 (Goodbye) ", "기억의 빈자리 (Emptiness in Memory) ",4,"나얼 (Naul)",2017);	addQuestion(q100);
        Question q101 = new Question("Ue9NG1hAr78", "넌 is 뭔들 (You're the best)", "데칼코마니 (Décalcomanie)  ", "나로 말할 것 같으면 (Yes I am) ", "아재개그 (AZE GAG) ",3,"마마무 (MAMAMOO)",2017);	addQuestion(q101);
        Question q102 = new Question("5wEjz3RdnKA", "낮보다는 밤 (Night Rather Than Day)", "L.I.E 엘라이 ", "멍청이 (TWIT) ", "덜덜덜(DDD) ",1,"EXID(이엑스아이디)",2017);	addQuestion(q102);
        Question q103 = new Question("J7gOqqbBW6w", "BUTTERFLY", "너에게 닿기를 (I Wish) ", "HAPPY ", "UNNATURAL ",2,"WJSN (우주소녀)",2017);	addQuestion(q103);
        Question q104 = new Question("vvkWaI91mLM", "저 별 (Star)", "And July (Feat. DEAN, DJ Friz) ", "Shut Up & Groove Feat.DEAN ", "널 너무 모르고 (Don't know you) ",4,"헤이즈 (Heize)",2017);	addQuestion(q104);
        Question q105 = new Question("IZ1t7CwfvEc", "Fly", "하드캐리(Hard Carry) ", "Never Ever ", "Look ",3,"GOT7(갓세븐)",2017);	addQuestion(q105);
        Question q106 = new Question("OwJPPaEyqhI", "New Face", "I LUV IT ", "Dancing King ", "GANGNAM STYLE ",1,"PSY",2017);	addQuestion(q106);
        Question q107 = new Question("8Oz7DG76ibY", "RE-BYE", "DINOSAUR", "우아해 (woo ah)", "낙하 (NAKKA) (with IU)",2,"AKMU",2017);	addQuestion(q107);
        Question q108 = new Question("axVvZrDz60k", "낮보다는 밤 (Night Rather Than Day)", "L.I.E 엘라이 ", "멍청이 (TWIT) ", "덜덜덜(DDD) ",4,"EXID(이엑스아이디)",2017);	addQuestion(q108);
        Question q109 = new Question("r1afdZk0qcI", "DRAMARAMA", "아름다워(Beautiful) ", "Love Killa ", "Shall We Dance ",1,"몬스타엑스(MONSTA X)",2017);	addQuestion(q109);
        Question q110 = new Question("hsh54g9JmC0", "Rain", "Starlight (Feat. DEAN) ", "Why ", "This Christmas ",4,"TAEYEON (태연)",2017);	addQuestion(q110);
        Question q111 = new Question("MBdVXkSdhwU", "Come Back Home", "DNA ", "피 땀 눈물 (Blood Sweat & Tears) ", "Hey - Mama!",2,"BTS (방탄소년단)",2017);	addQuestion(q111);
        Question q112 = new Question("ppOWR7ZLl7Q", "센치해(SENTIMENTAL)", "BABY BABY ", "좋더라 (I'M YOUNG) ", "LOVE ME LOVE ME ",4,"WINNER",2017);	addQuestion(q112);
        Question q113 = new Question("J0h8-OTC38I", "러시안 룰렛 (Russian Roulette)", "7월 7일 (One Of These Nights) ", "Rookie ", "Would U ",3,"Red Velvet (레드벨벳)",2017);	addQuestion(q113);
        Question q114 = new Question("TVUqLBRQom8", "내게 들려주고 싶은 말 (Dear Me)", "사계 (Four Seasons) ", "불티 (Spark) ", "Make Me Love You ",4,"TAEYEON (태연)",2017);	addQuestion(q114);
        Question q115 = new Question("rcEyUNeZqmY", "BAD LOVE", "WANT ", "MOVE ", "Press Your Number ",3,"TAEMIN (태민)",2017);	addQuestion(q115);
        Question q116 = new Question("42A-rFdralM", "봄날의 기억(Remember that)", "몇 년 후에 (A Few Years Later) ", "MOVIE ", "있어 희미하게 (Just us 2)",3,"BTOB(비투비)",2017);	addQuestion(q116);
        Question q117 = new Question("CyzEtbG-sxY", "아주 NICE (VERY NICE)", "CLAP ", "울고 싶지 않아 (Don't Wanna Cry) ", "Rock with you ",2,"SEVENTEEN (세븐틴)",2017);	addQuestion(q117);
        Question q118 = new Question("b9xndFqGJ4k", "멍청이 (TWIT)", "Lip & Hip ", "베베(BABE) ", "어때? (How's this?) ",3,"HyunA(현아)",2017);	addQuestion(q118);
        Question q119 = new Question("RIWRyggt-QY", "봄의 나라 이야기 (April Story)", "Dolphin ", "살짝 설렜어 (Nonstop) ", "LALALILALA ",1,"APRIL(에이프릴)",2017);	addQuestion(q119);
        Question q120 = new Question("W0cs6ciCt_k", "BERMUDA TRIANGLE", "Beautiful ", "어떻게 지내 (fall) ", "잊어버리지마 (Don’t Forget) ",2,"Crush(크러쉬)",2017);	addQuestion(q120);
        Question q121 = new Question("GeJAee3m3Ik", "METEOR", "BLUE MOON ", "Spell ", "둘 중에 골라 (Summer or Summer) ",2,"HYOLYN, CHANGMO(효린, 창모)",2017);	addQuestion(q121);
        Question q122 = new Question("1Q8J5nghxiM", "Bambi", "UN Village ", "비가와 (Rain) ", "Dream ",3,"SOYOU(소유), BAEKHYUN(백현)",2017);	addQuestion(q122);
        Question q123 = new Question("qYYJqWsBb1U", "LAST DANCE", "선물 (Gift) ", "무제 (Untitled, 2014) ", "Don't(그러지 마) feat. RM ",2,"MeloMance(멜로망스)",2017);	addQuestion(q123);
        Question q124 = new Question("NHglTopVlKQ", "Toy", "DRAMARAMA ", "Shall We Dance ", "YESTERDAY ",3,"블락비 (Block B)",2017);	addQuestion(q124);
        Question q125 = new Question("VQtonf1fv_s", "Merry & Happy", "SIGNAL ", "TT (티티) ", "CHEER UP ",2,"TWICE(트와이스)",2017);	addQuestion(q125);
        Question q126 = new Question("hZmoMyFXDoI", "썸 탈꺼야 (SOME)", "여행 (Travel) ", "나만, 봄 (Bom) ", "Listen to my word(내 얘길 들어봐)(A-ing)",1,"BOL4(볼빨간사춘기)",2017);	addQuestion(q126);
        Question q127 = new Question("f5Zedh_5DDM", "DRAMARAMA", "아름다워(Beautiful) ", "Love Killa ", "Shall We Dance ",2,"몬스타엑스(MONSTA X)",2017);	addQuestion(q127);
        Question q128 = new Question("7crt2Ip93VI", "몰랐니 (Lil' Touch)", "Fire Saturday (불토) ", "I'll be yours ", "Holiday ",3,"Girl's Day(걸스데이)",2017);	addQuestion(q128);
        Question q129 = new Question("obzb7nlpXZ0", "Artist", "BERMUDA TRIANGLE", "사랑이었다 (Feat. LUNA of f(x)) ", "너는 나 나는 너 (I am you, you are me) ",1,"지코 (ZICO)",2017);	addQuestion(q129);
        Question q130 = new Question("ZsYwEV_ge4Y", "시간을 달려서 (Rough)", "너 그리고 나 (NAVILLERA) ", "여름비 (Summer Rain) ", "밤 (Time for the moon night) ",3,"GFRIEND(여자친구)",2017);	addQuestion(q130);
        Question q131 = new Question("3q22SInyiX8", "Toy", "DRAMARAMA ", "Shall We Dance ", "YESTERDAY ",4,"블락비 (Block B)",2017);	addQuestion(q131);
        Question q132 = new Question("sUZeYOLp8Ys", "Trauma", "O Sole Mio(오솔레미오) ", "FLASH ", "해야 해 (Make it) ",2,"SF9",2017);	addQuestion(q132);
        Question q133 = new Question("Nu7OmSqHVng", "러시안 룰렛 (Russian Roulette)", "7월 7일 (One Of These Nights) ", "Rookie ", "Would U ",4,"Red Velvet (레드벨벳)",2017);	addQuestion(q133);
        Question q134 = new Question("zEkg4GBQumc", "아주 NICE (VERY NICE)", "CLAP ", "울고 싶지 않아 (Don't Wanna Cry) ", "Rock with you ",3,"SEVENTEEN (세븐틴)",2017);	addQuestion(q134);
        Question q135 = new Question("wLfHuClrQdI", "Bad Girl", "WEE WOO ", "WE LIKE ", "유리구두 (Glass Shoes) ",2,"PRISTIN(프리스틴)",2017);	addQuestion(q135);
        Question q136 = new Question("NRlDo3JuzfI", "Good Luck (굿 럭)", "빙글뱅글 (Bingle Bangle) ", "Excuse Me ", "No ",3,"AOA",2017);	addQuestion(q136);
        Question q137 = new Question("wKyMIrBClYw", "D (Half Moon)", "instagram ", "나빠 (NAPPA) ", "둘만의 세상으로 가 (Let Us Go) ",2,"DEAN",2017);	addQuestion(q137);
        Question q138 = new Question("vDxD4HwEFdY", "Destiny (나의 지구)", "종소리 (Twinkle) ", "지금, 우리 (Now, We) ", "찾아가세요(Lost N Found) ",2,"러블리즈(Lovelyz)",2017);	addQuestion(q138);
        Question q139 = new Question("b1kQvZhQ6_M", "첫 겨울이니까 (First Winter)", "포장마차 (Phocha) ", "술이 문제야 (Drunk On Love) ", "좋니 (Like it) ",4,"Jong Shin Yoon(윤종신)",2017);	addQuestion(q139);
        Question q140 = new Question("7cjZFjWBZI0", "컬러링북 (Coloring Book)", "다섯 번째 계절 (The fifth season) ", "HAPPY ", "살짝 설렜어 (Nonstop) ",1,"OH MY GIRL(오마이걸)",2017);	addQuestion(q140);
        Question q141 = new Question("b6l0x9xxk4k", "CALLING YOU", "불어온다 (NOT THE END) ", "사랑했나봐 (Loved) ", "걸어 (ALL IN) ",1,"Highlight(하이라이트)",2017);	addQuestion(q141);
        Question q142 = new Question("hvX3wWKOEa8", "예쁘다 (Pretty U)", "붐붐 (BOOMBOOM) ", "아주 NICE (VERY NICE) ", "터치 (TOUCH) ",4,"신화SHINHWA",2017);	addQuestion(q142);
        Question q143 = new Question("pC6tPEaAiYU", "RADIO", "톰보이 (TOMBOY) ", "True Love ", "이 노래가 클럽에서 나온다면 (Fire up)  ",2,"HYUKOH(혁오)",2017);	addQuestion(q143);
        Question q144 = new Question("6uJf2IT2Zh8", "RBB (Really Bad Boy)", "Power Up ", "Bad Boy ", "피카부 (Peek-A-Boo)",4,"Red Velvet (레드벨벳)",2017);	addQuestion(q144);
        Question q145 = new Question("sZVB_zCBlCU", "BUTTERFLY", "Easy (이지) ", "HAPPY ", "비밀이야 (Secret) ",3,"우주소녀(WJSN)",2017);	addQuestion(q145);
        Question q146 = new Question("YwN-CN9EjTg", "몰랐니 (Lil' Touch)", "Fire Saturday (불토) ", "I'll be yours ", "Holiday ",4,"Girls' Generation (소녀시대)",2017);	addQuestion(q146);
        Question q147 = new Question("aehbDMIEmnM", "겨울동화 (Winter Story)", "YOU AND I ", " 휘휘 (Hwi hwi) ", "BEcause ",3,"LABOUM(라붐)",2017);	addQuestion(q147);
        Question q148 = new Question("d9IxdwEFk1c", "Palette(팔레트)", "밤편지 (Through the Night) ", "이런 엔딩 (Ending Scene) ", "삐삐 (BBIBBI) ",1,"IU(아이유)",2017);	addQuestion(q148);
        Question q149 = new Question("afxLaQiLu-o", "떨어지는 낙엽까지도 (Falling Leaves are Beautiful)", "첫눈에 (First Sight) ", "Jenga (Feat. Gaeko) ", "비도 오고 그래서 (You,Clouds,Rain) ",4,"헤이즈 (Heize)",2017);	addQuestion(q149);
        Question q150 = new Question("1kcwvcbO8MI", "빛나리(Shine)", "청개구리 (Naughty boy) ", "얼굴 찌푸리지 말아요 (Plz Don’t Be Sad) ", "발키리 (Valkyrie) ",3,"하이라이트 (Highlight)",2017);	addQuestion(q150);
        Question q151 = new Question("BzYnNdJhZQw", "Palette(팔레트)", "밤편지 (Through the Night) ", "이런 엔딩 (Ending Scene) ", "삐삐 (BBIBBI) ",2,"IU(아이유)",2017);	addQuestion(q151);
        Question q152 = new Question("Xvjnoagk6GU", "New Face", "I LUV IT ", "Dancing King ", "GANGNAM STYLE ",2,"PSY",2017);	addQuestion(q152);
        Question q153 = new Question("KbXV2R_Yd1E", "Got That Boom", "I Like That ", "Who Dis? ", "LONELY ",4,"씨스타(SISTAR)",2017);	addQuestion(q153);
        Question q154 = new Question("9kaCAbIXuyg", "LAST DANCE", "선물 (Gift) ", "무제 (Untitled, 2014) ", "Don't(그러지 마) feat. RM ",3,"G-DRAGON",2017);	addQuestion(q154);
        Question q155 = new Question("Z1pGxkXyDvc", "나비효과 (Butterfly Effect)", "남이 될 수 있을까(We Loved) ", "소리 (Sori) ", "홀로 (HOLO) ",2,"볼빨간사춘기, 스무살(BOL4, 20 Years Of Age)",2017);	addQuestion(q155);
        Question q156 = new Question("Z3INNjAEqHk", "Face ID", "Rosario ", "연애소설 (LOVE STORY) ", "IndiGO ",3,"에픽하이 (EPIK HIGH)",2017);	addQuestion(q156);
        Question q157 = new Question("rRzxEiBLQCA", "Heart Shaker", "FINGERTIP ", "LIKEY ", "KNOCK KNOCK ",1,"트와이스(TWICE)",2017);	addQuestion(q157);
        Question q158 = new Question("Amq-qlqbjYA", "불장난 (Playing With Fire)", "LALISA ", "휘파람 (WHISTLE) ", "마지막처럼 (AS IF IT'S YOUR LAST)",4,"BLACKPINK",2017);	addQuestion(q158);
        Question q159 = new Question("b22Ed7f0D-0", "FIVE", "짐살라빔 (Zimzalabim) ", "응응(%%)(Eung Eung) ", "덤더럼(Dumhdurum) ",1,"에이핑크(Apink)",2017);	addQuestion(q159);
        Question q160 = new Question("IdssuxDdqKk", "Tempo", "Obsession ", "Don't fight the feeling ", "Ko Ko Bop ",4,"EXO(엑소)",2017);	addQuestion(q160);
        Question q161 = new Question("lnXXfYA91Y8", "FINGERTIP", "여름여름해 (Sunny Summer) ", "귀를 기울이면(LOVE WHISPER) ", "유리구슬 (Glass Bead) ",3,"여자친구(GFRIEND)",2017);	addQuestion(q161);
        Question q162 = new Question("EVaV7AwqBWg", "켜줘 (Light)", "에너제틱 (Energetic) ", "발키리 (Valkyrie) ", "Tell Me ",2,"워너원(Wanna One)",2017);	addQuestion(q162);
        Question q163 = new Question("V2hlQkVJZhE", "Heart Shaker", "FINGERTIP ", "LIKEY ", "KNOCK KNOCK ",3,"트와이스(TWICE)",2017);	addQuestion(q163);
        Question q164 = new Question("8A2t_tAjMz8", "Heart Shaker", "FINGERTIP ", "LIKEY ", "KNOCK KNOCK ",4,"트와이스(TWICE)",2017);	addQuestion(q164);
        Question q165 = new Question("EHgeGRU3wDI", "All With You", "Why Don’t You Know ", "Love U ", "벌써 12시 (Gotta Go) ",2,"청하 (CHUNG HA)",2017);	addQuestion(q165);
        Question q166 = new Question("kbdW2LaKlnw", "너무너무너무 (Very Very Very)", "Crush ", "소나기 (DOWNPOUR) ", "Whatta Man (Good man) ",3,"아이오아이 (I.O.I)",2017);	addQuestion(q166);
        Question q167 = new Question("i1n_1jrUEjU", "FINGERTIP", "여름여름해 (Sunny Summer) ", "귀를 기울이면(LOVE WHISPER) ", "유리구슬 (Glass Bead) ",1,"여자친구 (GFRIEND)",2017);	addQuestion(q167);
        Question q168 = new Question("mOo8bVzN9M8", "봄이 좋냐?? (What The Spring??)", "폰서트 (Phonecert)", "당신과는 천천히 (every moment with you)", "그러나 (however)",2,"10cm",2017);	addQuestion(q168);
        Question q169 = new Question("O136JYv3weQ", "염라(Karma)", "좋아해 (Love You) ", "너로피어오라(Flowering) ", "Today's Mood(오늘의 기분) ",2,"CHEEZE(치즈)",2017);	addQuestion(q169);
        Question q170 = new Question("OaG575r_Dyo", "솔직히 (Honestly…)", "봄인가 봐 (Spring Love) ", "유후 (You, Who?) ", "가을 타나 봐 (Fall in Fall) ",3,"에릭남X소미 (Eric Nam X Somi)",2017);	addQuestion(q170);
        Question q171 = new Question("Rh5ok0ljrzA", "Palette(팔레트)", "밤편지 (Through the Night) ", "이런 엔딩 (Ending Scene) ", "삐삐 (BBIBBI) ",3,"IU(아이유)",2017);	addQuestion(q171);
        Question q172 = new Question("wMCoQaE0LvQ", "Destiny (나의 지구)", "종소리 (Twinkle) ", "지금, 우리 (Now, We) ", "찾아가세요(Lost N Found) ",3,"러블리즈(Lovelyz)",2017);	addQuestion(q172);
        Question q173 = new Question("5e8-4mBiCSI", "Sorry", "Sixteen (Feat. Changmo) ", "INSIDE OUT ", "WHERE YOU AT ",4,"NU'EST W(뉴이스트 W)",2017);	addQuestion(q173);
        Question q174 = new Question("ur0hCdne2-s", "누아르 (Noir)", "가시나 (Gashina) ", "꼬리(TAIL) ", "보라빛 밤 (pporappippam) ",2,"선미(SUNMI)",2017);	addQuestion(q174);
        Question q175 = new Question("vcqImqOVE2U", "멍청이 (TWIT)", "Lip & Hip ", "베베(BABE) ", "어때? (How's this?) ",2,"HyunA(현아)",2017);	addQuestion(q175);
        Question q176 = new Question("v6_GwXU1lkg", "그날처럼 (Good old days)", "당신과는 천천히 (every moment with you) ", "흔들리는 꽃들 속에서 네 샴푸향이 느껴진거야 (Your Shampoo Scent In The Flowers)  ", "실버판테온 (SILVERPantheon) ",1,"장덕철(JANG DEOK CHEOL)",2017);	addQuestion(q176);
        Question q177 = new Question("CbNmRJCkwQs", "너는 어땠을까 (How about you)", "어떻게 지내 (I Need You) ", "all of my life ", "늦은 밤 너의 집 앞 골목길에서 (Late Night) ",3,"PARK WON(박원)",2017);	addQuestion(q177);
        Question q178 = new Question("NMPg8xVcjVs", "켜줘 (Light)", "에너제틱 (Energetic) ", "PARANOIA ", "Sixteen (Feat. Changmo) ",4,"Samuel(사무엘)",2017);	addQuestion(q178);
        Question q179 = new Question("tz23WFb8HM0", "You In Me", "주저하는 연인들을 위해 (for lovers who hesitate) ", "Bomb Bomb (밤밤) ", "가을밤에 든 생각 (A thought on an autumn night) ",1,"KARD",2017);	addQuestion(q179);
        Question q180 = new Question("nhRUyVMkaNI", "마리아 (Maria)", "Cold Blooded ", "눈누난나 (NUNU NANA) ", "Gucci ",4,"Jessi (제시)",2017);	addQuestion(q180);
        Question q181 = new Question("J6LAzgZi8N8", "Bad Girl", "WEE WOO ", "WE LIKE ", "유리구두 (Glass Shoes) ",3,"PRISTIN(프리스틴)",2017);	addQuestion(q181);
        Question q182 = new Question("zi_6oaQyckM", "Merry & Happy", "SIGNAL ", "TT (티티) ", "CHEER UP ",1,"TWICE(트와이스)",2017);	addQuestion(q182);
        Question q183 = new Question("GU7icQFVzHo", "FINGERTIP", "여름여름해 (Sunny Summer) ", "귀를 기울이면(LOVE WHISPER) ", "유리구슬 (Glass Bead) ",4,"여자친구(GFRIEND)",2017);	addQuestion(q183);
        Question q184 = new Question("ywAXliT2wKk", "Freeze (꼼짝마)", "Wonderful love (어마어마해) ", "Thumbs Up ", "Ready Or Not ",1,"MOMOLAND(모모랜드)",2017);	addQuestion(q184);
        Question q185 = new Question("TkSv0TkvTRs", "Freeze (꼼짝마)", "Wonderful love (어마어마해) ", "Thumbs Up ", "Ready Or Not ",2,"MOMOLAND(모모랜드)",2017);	addQuestion(q185);
        Question q186 = new Question("kRj4toENrnA", "MILLIONS", "EVERYDAY ", "FOOL ", "ISLAND ",4,"WINNER",2017);	addQuestion(q186);
        Question q187 = new Question("GsebMOWQiYM", "MILLIONS", "EVERYDAY ", "FOOL ", "ISLAND ",3,"WINNER",2017);	addQuestion(q187);
        Question q188 = new Question("AdfIfFGCqgo", "Christmas EveL", "Hellevator ", "Easy ", "소리꾼 (Thunderous) ",2,"Stray Kids(스트레이 키즈)",2017);	addQuestion(q188);
        Question q189 = new Question("_s5JQkJ6t6I", "WHY DON’T WE", "최고의 선물 (The Best Present) Prod. By PSY ", "FACE ", "Summer Taste ",2,"RAIN(비)",2017);	addQuestion(q189);
        Question q190 = new Question("uXcpLWB2eBA", "Sorry", "BABY ", "RETRO FUTURE ", "TWILIGHT ",1,"The Rose (더 로즈)",2017);	addQuestion(q190);
        Question q191 = new Question("2vJFn10XLQM", "Come Back Home", "DNA ", "피 땀 눈물 (Blood Sweat & Tears) ", "쏘쏘(SO-SO)",1,"BTS (방탄소년단)",2017);	addQuestion(q191);
        Question q192 = new Question("ohSpvSGXfhY", "She's a Baby", "SoulMate ", "Summer Hate (Feat. Rain(비)) ", "아무노래 (Any song) ",1,"지코 (ZICO)",2017);	addQuestion(q192);
        Question q193 = new Question("4Les0OrRuZ0", "넌 is 뭔들 (You're the best)", "데칼코마니 (Décalcomanie)  ", "나로 말할 것 같으면 (Yes I am) ", "아재개그 (AZE GAG) ",4,"마마무 (MAMAMOO)",2017);	addQuestion(q193);
        Question q194 = new Question("De5Lz3QzzWI", "여우야 (Yeowooya)", "괜찮아, 난 (I'm OK) (Feat. 이현우 (Lee Hyun Woo)) ", "좋은 사람 있으면 소개시켜줘 (Introduce me a good person) ", "안녕 (Hello) ",1,"조이 (JOY)",2017);	addQuestion(q194);
        Question q195 = new Question("E8mi-j2dKSk", "서울 밤 (Seoul Night)", "널 사랑하지 않아 (I Don't Love You) ", "소원 (Wish) ", "달고나 (Sugar and Me) ",3,"어반자카파(Urban Zakapa)",2017);	addQuestion(q195);
        Question q196 = new Question("gszuEGmVWlQ", "첫사랑 (first love)", "사랑하지 않은 것처럼 (The Love) ", "나비잠 (Sweet Dream) ", "십이월 이십오일의 고백 (My christmas wish) ",2,"버즈 (BUZZ)",2017);	addQuestion(q196);
        Question q197 = new Question("4vXnlbjMVU8", "Darling", "HEAVEN ", "우리 그만하자 (The Hardest Part) ", "그때 헤어지면 돼 (Only then) ",2,"로이킴, 김이지 (꽃잠프로젝트)",2017);	addQuestion(q197);
        Question q198 = new Question("5kjglHVyqc0", "여우야 (Yeowooya)", "괜찮아, 난 (I'm OK) (Feat. 이현우 (Lee Hyun Woo)) ", "좋은 사람 있으면 소개시켜줘 (Introduce me a good person) ", "안녕 (Hello) ",2,"조이 (JOY)",2017);	addQuestion(q198);
        Question q199 = new Question("n2TtQYuFfWw", "Bad Girl", "WEE WOO ", "WE LIKE ", "유리구두 (Glass Shoes) ",4,"프로미스_9 (fromis_9)",2017);	addQuestion(q199);
        Question q200 = new Question("iewWMqeMpqU", "SHINE FOREVER", "Fighter ", "네게만 집착해 (Stuck) ", "걸어 (ALL IN) ",1,"MONSTA X (몬스타엑스)",2017);	addQuestion(q200);
        Question q201 = new Question("WkuHLzMMTZM", "Cherry Bomb", "Superhuman ", "Sticker ", "Favorite (Vampire) ",1,"NCT 127 (엔시티 127)",2017);	addQuestion(q201);
        Question q202 = new Question("x-4WGzVOwAU", "거짓말 (Lie) (Feat. 이해리 of 다비치)", "Lost Without You(우리집을 못 찾겠군요) (feat.Bolbbalgan4(볼빨간사춘기)) ", "보자보자 (Let's see) ", "Counting Stars (feat. Beenzino) ",2,"매드클라운 (Mad Clown)",2017);	addQuestion(q202);
        Question q203 = new Question("rDG9I9nx0QU", "180도 (180 Degree)", "열애중 Love, ing ", "Love Recipe ", "헤어져줘서 고마워 (Thank you for Goodbye) ",1,"벤 (BEN)",2018);	addQuestion(q203);
        Question q204 = new Question("SkN_hWI6n28", "Darling", "HEAVEN ", "우리 그만하자 (The Hardest Part) ", "그때 헤어지면 돼 (Only then) ",4,"로이킴 (Roy Kim)",2018);	addQuestion(q204);
        Question q205 = new Question("VWqxvBQKwKQ", "LAST DANCE", "센치해(SENTIMENTAL) ", "아예 (AH YEAH) ", "꽃 길 (Flower Road) ",4,"BIGBANG",2018);	addQuestion(q205);
        Question q206 = new Question("pHtxTSiPh5I", "매일 봐요 (Everyday)", "Wind flower ", "별이 빛나는 밤 (Starry Night) ", "너나 해 (Egotistic) ",4,"마마무 (MAMAMOO)",2018);	addQuestion(q206);
        Question q207 = new Question("YBzJ0jmHv-4", "너를 만나 (Me After You)", "허전해 (empty) ", "우리 만남이 (But I'll Miss You) ", "Loveship ",1,"Paul Kim(폴킴)",2018);	addQuestion(q207);
        Question q208 = new Question("fHQkdIGue3k", "봄날의 기억(Remember that)", "몇 년 후에 (A Few Years Later) ", "MOVIE ", "너 없인 안 된다 (Only one for me)",4,"BTOB(비투비)",2018);	addQuestion(q208);
        Question q209 = new Question("Fm5iP0S1z9w", "Dance The Night Away", "YES or YES ", "What is Love? ", "Feel Special ",1,"TWICE(트와이스)",2018);	addQuestion(q209);
        Question q210 = new Question("IHNzOHi8sJs", "Ice Cream (with Selena Gomez)", "Lovesick Girls ", "Kill This Love ", "뚜두뚜두 (DDU-DU DDU-DU)",4,"BLACKPINK",2018);	addQuestion(q210);
        Question q211 = new Question("pSudEWBAYRE", "Love Shot", "Monster ", "For Life ", "Lotto ",1,"EXO (엑소)",2018);	addQuestion(q211);
        Question q212 = new Question("mtLgabce8KQ", "All With You", "Why Don’t You Know ", "Love U ", "벌써 12시 (Gotta Go) ",3,"청하 (CHUNG HA)",2018);	addQuestion(q212);
        Question q213 = new Question("sS0LCjOiIhc", "Fly", "하드캐리(Hard Carry) ", "Never Ever ", "Look ",4,"GOT7(갓세븐)",2018);	addQuestion(q213);
        Question q214 = new Question("9RUeTYiJCyA", "하드캐리(Hard Carry)", "ECLIPSE ", "Miracle ", "Lullaby ",4,"GOT7(갓세븐)",2018);	addQuestion(q214);
        Question q215 = new Question("nqMYG2Riq54", "Cereal (Feat.ZICO)", "멋지게 인사하는 법(Hello Tutorial) ", "우아해 (woo ah) ", "선물을 고르며 (A Gift!) ",2,"Zion.T",2018);	addQuestion(q215);
        Question q216 = new Question("_XyBa8QsVQU", "시간을 달려서 (Rough)", "너 그리고 나 (NAVILLERA) ", "여름비 (Summer Rain) ", "밤 (Time for the moon night) ",4,"GFRIEND(여자친구)",2018);	addQuestion(q216);
        Question q217 = new Question("J_CFBjAyPWE", "RBB (Really Bad Boy)", "Power Up ", "Bad Boy ", "피카부 (Peek-A-Boo)",3,"Red Velvet (레드벨벳)",2018);	addQuestion(q217);
        Question q218 = new Question("txWmd7QKFe8", "바나나차차 (BANANA CHACHA)", "I'm So Hot ", "뿜뿜 (BBoom Bboom) ", "BAAM ",4,"MOMOLAND (모모랜드)",2018);	addQuestion(q218);
        Question q219 = new Question("0FB2EoKTK_Q", "매일 봐요 (Everyday)", "Wind flower ", "별이 빛나는 밤 (Starry Night) ", "너나 해 (Egotistic) ",3,"마마무 (MAMAMOO)",2018);	addQuestion(q219);
        Question q220 = new Question("FD2mik4V5EE", "켜줘 (Light)", "에너제틱 (Energetic) ", "봄바람 (Spring Breeze) ", "부메랑 (BOOMERANG) ",3,"워너원(Wanna One)",2018);	addQuestion(q220);
        Question q221 = new Question("pmMjkMtpnTc", "켜줘 (Light)", "에너제틱 (Energetic) ", "봄바람 (Spring Breeze) ", "부메랑 (BOOMERANG) ",4,"워너원(Wanna One)",2018);	addQuestion(q221);
        Question q222 = new Question("ntWSnDV0MYs", "Good Luck (굿 럭)", "빙글뱅글 (Bingle Bangle) ", "Excuse Me ", "No ",2,"AOA",2018);	addQuestion(q222);
        Question q223 = new Question("JQGRg8XBnB4", "바나나차차 (BANANA CHACHA)", "I'm So Hot ", "뿜뿜 (BBoom Bboom) ", "BAAM ",3,"MOMOLAND (모모랜드)",2018);	addQuestion(q223);
        Question q224 = new Question("nM0xDI5R50E", "Palette(팔레트)", "밤편지 (Through the Night) ", "이런 엔딩 (Ending Scene) ", "삐삐 (BBIBBI) ",4,"IU(아이유)",2018);	addQuestion(q224);
        Question q225 = new Question("vecSVX1QYbQ", "사랑을 했다(LOVE SCENARIO)", "이별길(GOODBYE ROAD) ", "왜왜왜 (Why Why Why) ", "아예 (AH YEAH) ",1,"iKON",2018);	addQuestion(q225);
        Question q226 = new Question("TNWMZIf7eSg", "FLOWER SHOWER", "날라리 (LALALAY) ", "주인공 (Heroine) ", "사이렌 (Siren) ",4,"SUNMI(선미)",2018);	addQuestion(q226);
        Question q227 = new Question("Vl1kO9hObpA", "She's a Baby", "SoulMate ", "Summer Hate (Feat. Rain(비)) ", "아무노래 (Any song) ",2,"지코 (ZICO)",2018);	addQuestion(q227);
        Question q228 = new Question("b73BI9eUkjM", "SOLO", "LALISA ", "On The Ground ", "마지막처럼 (AS IF IT'S YOUR LAST) ",1,"JENNIE",2018);	addQuestion(q228);
        Question q229 = new Question("MS10Zz49FHE", "Rock with you", "Alligator ", "Shoot Out ", "GAMBLER ",3,"MONSTA X 몬스타엑스",2018);	addQuestion(q229);
        Question q230 = new Question("YfQzz00Oc_M", "별 (Star) (Little Prince)", "주지마 (Above Live) ", "GOOD (Feat. ELO) ", "시간이 들겠지 (It Takes Time) ",4,"Loco(로꼬)",2018);	addQuestion(q230);
        Question q231 = new Question("NY8VGNft-Zc", "몸 (BODY)", "도망가 (Run away) ", "탕!♡ (TANG!♡) ", "아낙네 (FIANCÉ) ",4,"MINO",2018);	addQuestion(q231);
        Question q232 = new Question("pBuZEGYXA6E", "IDOL", "FAKE LOVE ", "작은 것들을 위한 시 (Boy With Luv) ", "몰랐니 (Lil' Touch)",1,"BTS (방탄소년단)",2018);	addQuestion(q232);
        Question q233 = new Question("xRbPAVnqtcs", "썸 탈꺼야 (SOME)", "여행 (Travel) ", "나만, 봄 (Bom) ", "피카부 (Peek-A-Boo)",2,"BOL4(볼빨간사춘기)",2018);	addQuestion(q233);
        Question q234 = new Question("WBahioCWfFQ", "180도 (180 Degree)", "열애중 Love, ing ", "Love Recipe ", "헤어져줘서 고마워 (Thank you for Goodbye) ",2,"벤 (BEN)",2018);	addQuestion(q234);
        Question q235 = new Question("mAKsZ26SabQ", "Dance The Night Away", "YES or YES ", "What is Love? ", "Feel Special ",2,"TWICE(트와이스)",2018);	addQuestion(q235);
        Question q236 = new Question("i0p1bmr0EmE", "Dance The Night Away", "YES or YES ", "What is Love? ", "Feel Special ",3,"TWICE(트와이스)",2018);	addQuestion(q236);
        Question q237 = new Question("fB406grTgz4", "Darling", "HEAVEN ", "우리 그만하자 (The Hardest Part) ", "그때 헤어지면 돼 (Only then) ",3,"로이킴 (Roy Kim)",2018);	addQuestion(q237);
        Question q238 = new Question("2O6dRaBbFoo", "사랑을 했다(LOVE SCENARIO)", "이별길(GOODBYE ROAD) ", "왜왜왜 (Why Why Why) ", "아예 (AH YEAH) ",2,"iKON",2018);	addQuestion(q238);
        Question q239 = new Question("F4oHuML9U2A", "내가 설렐 수 있게 (Only one)", "너 그리고 나 (NAVILLERA) ", "NoNoNo ", "1도 없어 (I'm so sick) ",4,"Apink(에이핑크)",2018);	addQuestion(q239);
        Question q240 = new Question("uw_HR9jIJww", "떨어지는 낙엽까지도 (Falling Leaves are Beautiful)", "첫눈에 (First Sight) ", "Jenga (Feat. Gaeko) ", "비도 오고 그래서 (You,Clouds,Rain) ",3,"헤이즈 (Heize)",2018);	addQuestion(q240);
        Question q241 = new Question("IB6kViGA3rY", "별 (Star) (Little Prince)", "주지마 (Above Live) ", "GOOD (Feat. ELO) ", "시간이 들겠지 (It Takes Time) ",2,"로꼬 (Loco), 화사 (마마무)",2018);	addQuestion(q241);
        Question q242 = new Question("TM-gCW45YHc", "켜줘 (Light)", "에너제틱 (Energetic) ", "발키리 (Valkyrie) ", "Tell Me ",4,"인피니트(INFINITE)",2018);	addQuestion(q242);
        Question q243 = new Question("iwd8N6K-sLk", "Tempo", "Obsession ", "Don't fight the feeling ", "Ko Ko Bop ",1,"EXO (엑소)",2018);	addQuestion(q243);
        Question q244 = new Question("jt3Vdwrbhig", "RADIO", "톰보이 (TOMBOY) ", "True Love ", "이 노래가 클럽에서 나온다면 (Fire up)  ",3,"Kim Sung Kyu (김성규)",2018);	addQuestion(q244);
        Question q245 = new Question("aiHSVQy9xN8", "RBB (Really Bad Boy)", "Power Up ", "Bad Boy ", "피카부 (Peek-A-Boo)",2,"Red Velvet (레드벨벳)",2018);	addQuestion(q245);
        Question q246 = new Question("7C2z4GqqS5E", "IDOL", "FAKE LOVE ", "작은 것들을 위한 시 (Boy With Luv) ", "Hey - Mama!",2,"BTS (방탄소년단)",2018);	addQuestion(q246);
        Question q247 = new Question("pW0jpmncut4", "이별하러 가는 길 (The Way To Say Goodbye)", "내가 저지른 사랑(The love I committed) ", "하루도 그대를 사랑하지 않은 적이 없었다(There has never been a day I haven't loved you) ", "이 노래가 뭐라고 (This Song) ",3,"임창정",2018);	addQuestion(q247);
        Question q248 = new Question("Ib674A1yMtg", "Love Shot", "Tempo ", "花요일 (Blooming Day) ", "Hey - Mama!",3,"EXO-CBX (첸백시)",2018);	addQuestion(q248);
        Question q249 = new Question("hhXDFl6tmVY", "떨어지는 낙엽까지도 (Falling Leaves are Beautiful)", "첫눈에 (First Sight) ", "Jenga (Feat. Gaeko) ", "비도 오고 그래서 (You,Clouds,Rain) ",2,"헤이즈 (Heize)",2018);	addQuestion(q249);
        Question q250 = new Question("WZwr2a_lFWY", "비올레타 (Violeta)", "환상동화 (Secret Story of the Swan) ", "ELEVEN ", "라비앙로즈 (La Vie en Rose) ",4,"아이즈원 (IZ*ONE)",2018);	addQuestion(q250);
        Question q251 = new Question("IWJUPY-2EIM", "RBB (Really Bad Boy)", "Power Up ", "Bad Boy ", "피카부 (Peek-A-Boo)",1,"Red Velvet (레드벨벳)",2018);	addQuestion(q251);
        Question q252 = new Question("L15ZZX9n56M", "All With You", "Why Don’t You Know ", "Love U ", "벌써 12시 (Gotta Go) ",4,"청하 (CHUNG HA)",2018);	addQuestion(q252);
        Question q253 = new Question("Nso7yIr8QVM", "너는 어땠을까 (How about you)", "어떻게 지내 (I Need You) ", "all of my life ", "늦은 밤 너의 집 앞 골목길에서 (Late Night) ",1,"Noel(노을)",2018);	addQuestion(q253);
        Question q254 = new Question("oT8eXpXymmA", "Sorry", "BABY ", "RETRO FUTURE ", "TWILIGHT ",3,"Triple H(트리플 H)",2018);	addQuestion(q254);
        Question q255 = new Question("3K38Fc1SV5c", "HANN (Alone)(한(一))", "LATATA", "LA DI DA", "DUN DUN",1,"(여자)아이들((G)I-DLE)",2018);	addQuestion(q255);
        Question q256 = new Question("H8NCOA2bK6k", "매일 봐요 (Everyday)", "Wind flower ", "별이 빛나는 밤 (Starry Night) ", "너나 해 (Egotistic) ",2,"마마무 (MAMAMOO)",2018);	addQuestion(q256);
        Question q257 = new Question("I5_BQAtwHws", "겨울동화 (Winter Story)", "YOU AND I ", " 휘휘 (Hwi hwi) ", "BEcause ",2,"Dreamcatcher(드림캐쳐)",2018);	addQuestion(q257);
        Question q258 = new Question("F4qfN5UeFvQ", "FLOWER SHOWER", "날라리 (LALALAY) ", "주인공 (Heroine) ", "사이렌 (Siren) ",3,"SUNMI(선미)",2018);	addQuestion(q258);
        Question q259 = new Question("rW9r_1ys2ec", "BUNGEE (Fall in Love)", "바나나 알러지 원숭이 (Banana allergy monkey) ", "LIAR LIAR ", "Listen to my word(내 얘길 들어봐)(A-ing)",2,"OH MY GIRL BANHANA(오마이걸 반하나)",2018);	addQuestion(q259);
        Question q260 = new Question("LbWt67vVNgc", "매일 봐요 (Everyday)", "Wind flower ", "별이 빛나는 밤 (Starry Night) ", "너나 해 (Egotistic) ",1,"마마무 (MAMAMOO)",2018);	addQuestion(q260);
        Question q261 = new Question("0N9U2hs03Tc", "180도 (180 Degree)", "열애중 Love, ing ", "Love Recipe ", "헤어져줘서 고마워 (Thank you for Goodbye) ",3,"벤 (BEN)",2018);	addQuestion(q261);
        Question q262 = new Question("9mQk7Evt6Vs", "HANN (Alone)(한(一))", "LATATA", "LA DI DA", "DUN DUN",2,"(여자)아이들((G)I-DLE)",2018);	addQuestion(q262);
        Question q263 = new Question("hF6Wds75rjg", "No", "미 (Me) ", "BLACK DRESS ", "Good Luck (굿 럭) ",3,"CLC",2018);	addQuestion(q263);
        Question q264 = new Question("jd2DxTR0znU", "IndiGO", "띵 (DDING) ", "METEOR ", "또 하루 (Lonely Night)(feat. GAEKO(개코)) ",1,"JUSTHIS(저스디스), Kid Milli, NO:EL, Young B(영비)",2018);	addQuestion(q264);
        Question q265 = new Question("8n6pRkpVsyU", "The Stealer", "THRILL RIDE ", "No Air ", "Giddy Up ",3,"THE BOYZ (더보이즈)",2018);	addQuestion(q265);
        Question q266 = new Question("1icPJAhI2TA", "잘할게 (I will)", "미안해 (lie) ", "뻔한남자 (The Ordinary Man) ", "Darling ",2,"양다일 (Yang Da Il)",2018);	addQuestion(q266);
        Question q267 = new Question("Y90uGW5z2MM", "빛나리(Shine)", "청개구리 (Naughty boy) ", "얼굴 찌푸리지 말아요 (Plz Don’t Be Sad) ", "발키리 (Valkyrie) ",2,"펜타곤(PENTAGON)",2018);	addQuestion(q267);
        Question q268 = new Question("uexk7jWXYmU", "꽃이야 (Myflower)", "부를게 (Call Your Name) ", "뚫고 지나가요 (Right Through Me) ", "Zombie ",1,"JBJ",2018);	addQuestion(q268);
        Question q269 = new Question("9iPLjmz3_U4", "FINGERTIP", "여름여름해 (Sunny Summer) ", "귀를 기울이면(LOVE WHISPER) ", "유리구슬 (Glass Bead) ",2,"여자친구 (GFRIEND)",2018);	addQuestion(q269);
        Question q270 = new Question("42iMZrYDEM4", "이별하러 가는 길 (The Way To Say Goodbye)", "내가 저지른 사랑(The love I committed) ", "하루도 그대를 사랑하지 않은 적이 없었다(There has never been a day I haven't loved you) ", "이 노래가 뭐라고 (This Song) ",1,"임한별(Onestar)",2018);	addQuestion(q270);
        Question q271 = new Question("QIN5_tJRiyY", "Dun Dun Dance", "WINDY DAY ", "불꽃놀이 (REMEMBER ME) ", "비밀정원 (Secret Garden) ",4,"오마이걸 (OH MY GIRL)",2018);	addQuestion(q271);
        Question q272 = new Question("QG8bUKBT9FI", "넌 is 뭔들 (You're the best)", "SELFISH (Feat. SEULGI(슬기) Of Red Velvet) ", "나로 말할 것 같으면 (Yes I am) ", "데칼코마니 (Décalcomanie)  ",2,"Moon Byul (문별)",2018);	addQuestion(q272);
        Question q273 = new Question("kMRLzSQorK0", "답장 (Reply)", "숨(Breath) ", "굿바이 (Goodbye) ", "기억의 빈자리 (Emptiness in Memory) ",1,"김동률 (KIM DONG RYUL)",2018);	addQuestion(q273);
        Question q274 = new Question("3w5iMGSHvsE", "첫사랑 (first love)", "사랑하지 않은 것처럼 (The Love) ", "나비잠 (Sweet Dream) ", "십이월 이십오일의 고백 (My christmas wish) ",1,"에피톤 프로젝트 (Epitone Project)",2018);	addQuestion(q274);
        Question q275 = new Question("RrvdjyIL0fA", "Dun Dun Dance", "WINDY DAY ", "불꽃놀이 (REMEMBER ME) ", "비밀정원 (Secret Garden) ",3,"오마이걸 (OH MY GIRL)",2018);	addQuestion(q275);
        Question q276 = new Question("AJqhKWo89FQ", "Dun Dun Dance", "WINDY DAY ", "불꽃놀이 (REMEMBER ME) ", "비밀정원 (Secret Garden) ",2,"오마이걸 (OH MY GIRL)",2018);	addQuestion(q276);
        Question q277 = new Question("D2MhwXZ8IgM", "비밀정원 (Secret Garden)", "FIVE ", "예쁜 게 죄 (Oh! My mistake) ", "파랑새 (The Blue Bird) ",3,"에이프릴(APRIL)",2018);	addQuestion(q277);
        Question q278 = new Question("Ks6I2rtI4ms", "꽃이야 (Myflower)", "부를게 (Call Your Name) ", "뚫고 지나가요 (Right Through Me) ", "Zombie ",2,"JBJ",2018);	addQuestion(q278);
        Question q279 = new Question("Uh_6PY9am_0", "비밀정원 (Secret Garden)", "FIVE ", "예쁜 게 죄 (Oh! My mistake) ", "파랑새 (The Blue Bird) ",4,"에이프릴 (APRIL)",2018);	addQuestion(q279);
        Question q280 = new Question("3Uyl_ifBiLE", "과제곡 (The Assignment Song)", "Traffic light(신호등) ", "좋은 날 (Good Day) ", "It's you (Feat.ZICO) ",4,"샘김(Sam Kim)",2018);	addQuestion(q280);
        Question q281 = new Question("Nu2yQ1zYDYU", "빛나리(Shine)", "청개구리 (Naughty boy) ", "얼굴 찌푸리지 말아요 (Plz Don’t Be Sad) ", "발키리 (Valkyrie) ",1,"펜타곤(PENTAGON)",2018);	addQuestion(q281);
        Question q282 = new Question("obX621oa9RM", "Destiny (나의 지구)", "종소리 (Twinkle) ", "지금, 우리 (Now, We) ", "찾아가세요(Lost N Found) ",4,"러블리즈(Lovelyz)",2018);	addQuestion(q282);
        Question q283 = new Question("DNH7X46gMjQ", "The Stealer", "THRILL RIDE ", "No Air ", "Giddy Up ",4,"THE BOYZ(더보이즈)",2018);	addQuestion(q283);
        Question q284 = new Question("d1D1SJ-KqaQ", "MILLIONS", "EVERYDAY ", "FOOL ", "ISLAND ",2,"WINNER",2018);	addQuestion(q284);
        Question q285 = new Question("900X9fDFLc4", "Roller Coaster", "Snapping ", "그대라는 시 (All about you) ", "Stay Tonight ",1,"청하 (CHUNG HA)",2018);	addQuestion(q285);
        Question q286 = new Question("J1L3nDC4mEk", "켜줘 (Light)", "에너제틱 (Energetic) ", "발키리 (Valkyrie) ", "Tell Me ",1,"워너원 (Wanna One)",2018);	addQuestion(q286);
        Question q287 = new Question("SKPyCQn9UME", "별 (Star) (Little Prince)", "주지마 (Above Live) ", "GOOD (Feat. ELO) ", "시간이 들겠지 (It Takes Time) ",1,"로꼬 (Loco) & 유성은 (U Seungeun)",2018);	addQuestion(q287);
        Question q288 = new Question("5uEVbzD_Ta4", "과제곡 (The Assignment Song)", "Traffic light(신호등) ", "좋은 날 (Good Day) ", "It's you (Feat.ZICO) ",3,"멜로망스 (MeloMance)",2018);	addQuestion(q288);
        Question q289 = new Question("OmetCO_ZFnI", "Sorry", "BABY ", "RETRO FUTURE ", "TWILIGHT ",2,"The Rose (더 로즈)",2018);	addQuestion(q289);
        Question q290 = new Question("wJi2_U_rGwk", "벚꽃연가 (Cherry Blossom Love Song)", "사월이 지나면 우리 헤어져요 (Beautiful goodbye) ", "너를 위해 (For You) ", "Hey - Mama!",1,"첸 (CHEN)",2018);	addQuestion(q290);
        Question q291 = new Question("vbZbnpbVc0w", "솔직히 (Honestly…)", "봄인가 봐 (Spring Love) ", "유후 (You, Who?) ", "가을 타나 봐 (Fall in Fall) ",1,"에릭남 (Eric Nam)",2018);	addQuestion(q291);
        Question q292 = new Question("106613NbPQ0", "BERMUDA TRIANGLE", "Cereal (Feat.ZICO) ", "우아해 (woo ah) ", "니가 하면 로맨스 (You call it romance) Feat. 다비치(Davichi) ",2,"크러쉬 (Crush)",2018);	addQuestion(q292);
        Question q293 = new Question("FvhWloYLj54", "If You", "Is You ", "All With You ", "그대라는 시 (All about you) ",2,"에일리 (Ailee)",2018);	addQuestion(q293);
        Question q294 = new Question("1eq9F-t02GY", "너무너무너무 (Very Very Very)", "Crush ", "소나기 (DOWNPOUR) ", "Whatta Man (Good man) ",4,"아이오아이 (I.O.I)",2018);	addQuestion(q294);
        Question q295 = new Question("5a-tqIQc8RM", "야 하고 싶어 (CALL YOU BAE) Feat. XIUMIN(시우민) of EXO", "PADO ", "신청곡 (Song request) ", "어른 (Grown Ups) ",4,"Sondia",2018);	addQuestion(q295);
        Question q296 = new Question("WczI5vaARzg", "홀로 (HOLO)", "소리 (Sori) ", "We All Lie ", "니 소식 (Your regards) ",2,"이수현 (Lee Suhyun of AKMU)",2018);	addQuestion(q296);
        Question q297 = new Question("TSA9VZduuZ4", "JEALOUSY", "FOLLOW ", "FANTASIA ", "Rush Hour ",1,"MONSTA X (몬스타엑스)",2018);	addQuestion(q297);
        Question q298 = new Question("0AUFyFEt35g", "일곱 번째 감각 (The 7th Sense)", "BOSS ", "Baby Don't Stop ", "Make A Wish (Birthday Song) ",2,"NCT U (엔시티 유)",2018);	addQuestion(q298);
        Question q299 = new Question("SdzLl-XpJt0", "DON'T TOUCH ME", "Shut Up ", "다시 여기 바닷가(Beach Again) ", "몰랐니 (Lil' Touch)",4,"소녀시대-Oh!GG (Girls' Generation-Oh!GG)",2018);	addQuestion(q299);
        Question q300 = new Question("ZbB4SYJNuTo", "Lo Siento (Feat. Leslie Grace)", "House Party ", "Jopping ", "Atlantis ",1,"SUPER JUNIOR (슈퍼주니어)",2018);	addQuestion(q300);
        Question q301 = new Question("k0DqRstCgj4", "일곱 번째 감각 (The 7th Sense)", "BOSS ", "Baby Don't Stop ", "Make A Wish (Birthday Song) ",3,"NCT U (엔시티 유)",2018);	addQuestion(q301);
        Question q302 = new Question("RtRtLf84I2M", "하드캐리(Hard Carry)", "ECLIPSE ", "Miracle ", "Lullaby ",3,"GOT7(갓세븐)",2018);	addQuestion(q302);
        Question q303 = new Question("fJubafP3IMI", "밤하늘의 저 별처럼 (Midnight)", "가끔 이러다 (Sometimes) ", "Stay With Me ", "Ring Ring ",2,"Punch(펀치)",2019);	addQuestion(q303);
        Question q304 = new Question("Cp56JdkmE9s", "고고베베 (gogobebe)", "딩가딩가 (Dingga) ", "AYA ", "WANNA BE MYSELF ",1,"마마무 (MAMAMOO)",2019);	addQuestion(q304);
        Question q305 = new Question("j2aQ_NqeTNw", "Rose", "음 (Mmmh) ", "사랑, 하자 (Let’s Love) ", "괜찮아도 괜찮아 (That's okay) ",4,"디오 (D.O.)",2019);	addQuestion(q305);
        Question q306 = new Question("Xp8Ep1W-azw", "답장 (Reply)", "숨(Breath) ", "굿바이 (Goodbye) ", "기억의 빈자리 (Emptiness in Memory) ",3,"박효신(Park Hyo Shin)",2019);	addQuestion(q306);
        Question q307 = new Question("lHEOj3d7YS4", "If You", "Is You ", "All With You ", "그대라는 시 (All about you) ",4,"태연 (TAEYEON)",2019);	addQuestion(q307);
        Question q308 = new Question("hNnoi32CyrA", "당신과는 천천히 (every moment with you)", "폰서트 (Phonecert)", "그러나 (however)", "고백 (Go Back)",3,"10cm",2019);	addQuestion(q308);
        Question q309 = new Question("AsXxuIdpkWM", "썸 탈꺼야 (SOME)", "여행 (Travel) ", "나만, 봄 (Bom) ", "피카부 (Peek-A-Boo)",3,"BOL4(볼빨간사춘기)",2019);	addQuestion(q309);
        Question q310 = new Question("OoMIAo0a2TA", "나빠 (NAPPA)", "놓아줘 (with 태연) ", "둘만의 세상으로 가 (Let Us Go) ", "아무노래 (Any song) ",1,"Crush(크러쉬)",2019);	addQuestion(q310);
        Question q311 = new Question("LrggmyDhWBo", "FLOWER SHOWER", "날라리 (LALALAY) ", "주인공 (Heroine) ", "사이렌 (Siren) ",2,"SUNMI(선미)",2019);	addQuestion(q311);
        Question q312 = new Question("84R5AS0tfkQ", "그냥 안아달란 말야 (Just hug me)", "너에게 못했던 내 마지막 말은 (Unspoken Words) ", "내 옆에 그대인걸 (Beside me) ", "하늘바라기 (Hopefully sky) ",2,"다비치 (DAVICHI)",2019);	addQuestion(q312);
        Question q313 = new Question("GQqyCeKf8rw", "소리 (Sori)", "한숨 (BREATHE) ", "누구 없소 (NO ONE) ", "홀로 (HOLO) ",3,"LEE HI",2019);	addQuestion(q313);
        Question q314 = new Question("kFhf7pjRRjA", "너는 어땠을까 (How about you)", "어떻게 지내 (I Need You) ", "all of my life ", "늦은 밤 너의 집 앞 골목길에서 (Late Night) ",4,"Noel(노을)",2019);	addQuestion(q314);
        Question q315 = new Question("eMZmNisWFvM", "Bye bye my blue", "니 소식 (Your regards) ", "다시 난, 여기 (Here I Am Again) ", "쏘쏘(SO-SO)",2,"송하예 Ha Yea Song",2019);	addQuestion(q315);
        Question q316 = new Question("udGwca1HBM4", "컬러링북 (Coloring Book)", "다섯 번째 계절 (The fifth season) ", "Dolphin ", "HAPPY ",2,"OH MY GIRL(오마이걸)",2019);	addQuestion(q316);
        Question q317 = new Question("pNfTK39k55U", "달라달라(DALLA DALLA)", "ICY ", "WANNABE ", "Not Shy ",1,"ITZY(있지)",2019);	addQuestion(q317);
        Question q318 = new Question("yOZNN5xrK2o", "봄이 좋냐?? (What The Spring??)", "당신과는 천천히 (every moment with you) ", "폰서트 (Phonecert) ", "흔들리는 꽃들 속에서 네 샴푸향이 느껴진거야 (Your Shampoo Scent In The Flowers)  ",2,"장범준 (Beom June Jang)",2019);	addQuestion(q318);
        Question q319 = new Question("lpka6ymCkIY", "떨어지는 낙엽까지도 (Falling Leaves are Beautiful)", "첫눈에 (First Sight) ", "Jenga (Feat. Gaeko) ", "비도 오고 그래서 (You,Clouds,Rain) ",1,"헤이즈 (Heize)",2019);	addQuestion(q319);
        Question q320 = new Question("ir7G_H0LFJw", "IndiGO", "띵 (DDING) ", "METEOR ", "또 하루 (Lonely Night)(feat. GAEKO(개코)) ",2,"Jvcki Wai, Young B(영비), Osshun Gum(오션검), Han Yo Han(한요한)",2019);	addQuestion(q320);
        Question q321 = new Question("6oanIo_2Z4Q", "HANN (Alone)(한(一))", "LA DI DA", "LION", "Senorita",3,"(여자)아이들((G)I-DLE)",2019);	addQuestion(q321);
        Question q322 = new Question("ScSn235gQx0", "낮보다는 밤 (Night Rather Than Day)", "L.I.E 엘라이 ", "멍청이 (TWIT) ", "덜덜덜(DDD) ",3,"Hwa Sa(화사)",2019);	addQuestion(q322);
        Question q323 = new Question("lOrU0MH0bMk", "IndiGO", "띵 (DDING) ", "METEOR ", "또 하루 (Lonely Night)(feat. GAEKO(개코)) ",3,"창모 CHANGMO",2019);	addQuestion(q323);
        Question q324 = new Question("_-QY40Reub8", "에너제틱 (Energetic)", "PARANOIA ", "Antidote ", "뭐해 (What are you up to) ",4,"강다니엘(KANGDANIEL)",2019);	addQuestion(q324);
        Question q325 = new Question("PALjhRpnfbk", "MILLIONS", "EVERYDAY ", "FOOL ", "ISLAND ",1,"WINNER",2019);	addQuestion(q325);
        Question q326 = new Question("oDJ4ct59NC4", "Birthday", "What You Waiting For ", "DUMB DUMB ", "XOXO ",1,"JEON SOMI (전소미)",2019);	addQuestion(q326);
        Question q327 = new Question("DsouXE31I6k", "봄(Spring)", "SPICY ", "No ", "미 (Me) ",1,"박봄(PARK BOM)",2019);	addQuestion(q327);
        Question q328 = new Question("eP4ga_fNm-E", "내게 들려주고 싶은 말 (Dear Me)", "사계 (Four Seasons) ", "불티 (Spark) ", "Make Me Love You ",3,"TAEYEON (태연)",2019);	addQuestion(q328);
        Question q329 = new Question("D1PvIWdJ8xo", "strawberry moon", "에잇 (eight) ", "블루밍 (Blueming) ", "Celebrity ",3,"IU(아이유)",2019);	addQuestion(q329);
        Question q330 = new Question("gWHgpJ3m-x8", "비가 내리는 날에는 (On A Rainy Day)", "My Love ", "별의 조각 (Stardust) ", "밤하늘의 별을 (Shiny Star) ",1,"YOUNHA(윤하)",2019);	addQuestion(q330);
        Question q331 = new Question("6eEZ7DJMzuk", "Panorama", "환상동화 (Secret Story of the Swan) ", "FIESTA ", "비올레타 (Violeta) ",4,"IZ*ONE (아이즈원)",2019);	addQuestion(q331);
        Question q332 = new Question("4HG_CJzyX6A", "내게 들려주고 싶은 말 (Dear Me)", "사계 (Four Seasons) ", "불티 (Spark) ", "Make Me Love You ",2,"TAEYEON (태연)",2019);	addQuestion(q332);
        Question q333 = new Question("kXAvbHPdTZ0", "사랑에 연습이 있었다면 (If there was practice in love)", "조금 취했어 (I'm a little drunk) ", "이제 나만 믿어요 (Trust in Me) ", "HERO ",1,"Lim Jae Hyun(임재현)",2019);	addQuestion(q333);
        Question q334 = new Question("tlHTOlnPcbs", "어디에도 (No matter where)", "사랑이 식었다고 말해도 돼 (My love has faded away) ", "처음처럼 (BLOOM) ", "그대라는 사치 (Amazing You) ",2,"Monday Kiz(먼데이 키즈)",2019);	addQuestion(q334);
        Question q335 = new Question("JrOrlhjIYVk", "벚꽃연가 (Cherry Blossom Love Song)", "사월이 지나면 우리 헤어져요 (Beautiful goodbye) ", "너를 위해 (For You) ", "Hey - Mama!",2,"첸 (CHEN)",2019);	addQuestion(q335);
        Question q336 = new Question("A0gP4id3Gxc", "서울 밤 (Seoul Night)", "널 사랑하지 않아 (I Don't Love You) ", "소원 (Wish) ", "달고나 (Sugar and Me) ",1,"어반자카파(Urban Zakapa)",2019);	addQuestion(q336);
        Question q337 = new Question("2cevbhEqQF4", "LA DI DA", "LATATA", "DUN DUN", "Senorita",4,"(여자)아이들((G)I-DLE)",2019);	addQuestion(q337);
        Question q338 = new Question("4Sd09Mruhnk", "다시 난, 여기 (Here I Am Again)", "쏘쏘(SO-SO)", "솔직하게 말해서 나 (To be honest) ", "열애중 Love, ing ",3,"Kim na young(김나영)",2019);	addQuestion(q338);
        Question q339 = new Question("47Vz-ptyKbQ", "Face ID", "Rosario ", "내 얘기 같아 (Based On A True Story) ", "술이 달다 (LOVEDRUNK) ",4,"EPIK HIGH (에픽하이)",2019);	addQuestion(q339);
        Question q340 = new Question("XcbEM7j_ARQ", "첫 겨울이니까 (First Winter)", "포장마차 (Phocha) ", "술이 문제야 (Drunk On Love) ", "좋니 (Like it) ",3,"JANG HYEJIN (장혜진), YUN MIN SOO (윤민수)",2019);	addQuestion(q340);
        Question q341 = new Question("x95oZNxW5Rc", "Cherry Bomb", "Superhuman ", "Sticker ", "Favorite (Vampire) ",2,"NCT 127 (엔시티 127)",2019);	addQuestion(q341);
        Question q342 = new Question("deV_DmHKwjc", "Roller Coaster", "Snapping ", "그대라는 시 (All about you) ", "Stay Tonight ",2,"청하 (CHUNG HA)",2019);	addQuestion(q342);
        Question q343 = new Question("ij0SQZcqnPU", "야 하고 싶어 (CALL YOU BAE) Feat. XIUMIN(시우민) of EXO", "PADO ", "신청곡 (Song request) ", "어른 (Grown Ups) ",3,"LeeSoRa(이소라)",2019);	addQuestion(q343);
        Question q344 = new Question("QUXNXYB1tq0", "첫사랑 (first love)", "사랑하지 않은 것처럼 (The Love) ", "나비잠 (Sweet Dream) ", "십이월 이십오일의 고백 (My christmas wish) ",4,"Jung Seung Hwan(정승환)",2019);	addQuestion(q344);
        Question q345 = new Question("uR8Mrt1IpXg", "Psycho", "음파음파 (Umpah Umpah) ", "짐살라빔 (Zimzalabim) ", "Queendom ",1,"Red Velvet (레드벨벳)",2019);	addQuestion(q345);
        Question q346 = new Question("Pm0_G8Zl0ek", "사랑을 했다(LOVE SCENARIO)", "이별길(GOODBYE ROAD) ", "왜왜왜 (Why Why Why) ", "아예 (AH YEAH) ",4,"WINNER",2019);	addQuestion(q346);
        Question q347 = new Question("zndvqTc4P9I", "달라달라(DALLA DALLA)", "ICY ", "WANNABE ", "Not Shy ",2,"ITZY(있지)",2019);	addQuestion(q347);
        Question q348 = new Question("m3DZsBw5bnE", "우아해 (woo ah)", "DINOSAUR", "어떻게 이별까지 사랑하겠어, 널 사랑하는 거지(How can I love the heartbreak, you`re the one I love)", "낙하 (NAKKA) (with IU) ",3,"AKMU",2019);	addQuestion(q348);
        Question q349 = new Question("3C3hIJg4rHo", "Rock with you", "Alligator ", "Shoot Out ", "GAMBLER ",2,"MONSTA X 몬스타엑스",2019);	addQuestion(q349);
        Question q350 = new Question("VpaUh_BGqE0", "기다렸다 가 (nosedive)", "옥탑방 (Rooftop) ", "Pump It Up ", "Nostalgia ",2,"N.Flying(엔플라잉)",2019);	addQuestion(q350);
        Question q351 = new Question("uxmP4b2a0uY", "Tempo", "Obsession ", "Don't fight the feeling ", "Ko Ko Bop ",2,"EXO (엑소)",2019);	addQuestion(q351);
        Question q352 = new Question("AtNBhPxVwh0", "너를 위해 (For You)", "Dancing King ", "What a life ", "있어 희미하게 (Just us 2)",3,"세훈&찬열 (EXO-SC)",2019);	addQuestion(q352);
        Question q353 = new Question("I66oFXdf0KU", "화(火花)(HWAA)", "Oh my god", "Uh-Oh", "DUN DUN",3,"(여자)아이들((G)I-DLE)",2019);	addQuestion(q353);
        Question q354 = new Question("mrAIqeULUL0", "썸 탈꺼야 (SOME)", "여행 (Travel) ", "나만, 봄 (Bom) ", "워커홀릭 (Workaholic)",4,"BOL4(볼빨간사춘기)",2019);	addQuestion(q354);
        Question q355 = new Question("ByHNlfmmT-w", "We don't talk together", "비가 오는 날엔 (2021)(On Rainy Day) ", "헤픈 우연 (HAPPEN) ", "Jenga (Feat. Gaeko) ",1,"헤이즈 (Heize)",2019);	addQuestion(q355);
        Question q356 = new Question("PdDfuWJc9dA", "홀로 (HOLO)", "소리 (Sori) ", "We All Lie ", "니 소식 (Your regards) ",3,"하진 (Ha Jin)",2019);	addQuestion(q356);
        Question q357 = new Question("vHS9E6JFja8", "Psycho", "음파음파 (Umpah Umpah) ", "짐살라빔 (Zimzalabim) ", "Queendom ",2,"Red Velvet (레드벨벳)",2019);	addQuestion(q357);
        Question q358 = new Question("499YUeNoYVE", "내가 설렐 수 있게 (Only one)", "너 그리고 나 (NAVILLERA) ", "응응(%%)(Eung Eung) ", "덤더럼(Dumhdurum) ",3,"Apink(에이핑크)",2019);	addQuestion(q358);
        Question q359 = new Question("MBSpoTozBdg", "RADIO", "톰보이 (TOMBOY) ", "True Love ", "이 노래가 클럽에서 나온다면 (Fire up)  ",4,"우디 (Woody)",2019);	addQuestion(q359);
        Question q360 = new Question("6tl-MG38-0E", "하드캐리(Hard Carry)", "ECLIPSE ", "Miracle ", "Lullaby ",2,"GOT7(갓세븐)",2019);	addQuestion(q360);
        Question q361 = new Question("SKAsHxi1Tlc", "너를 위해 (For You)", "Dancing King ", "What a life ", "있어 희미하게 (Just us 2)",4,"세훈&찬열 (EXO-SC)",2019);	addQuestion(q361);
        Question q362 = new Question("XsX3ATc3FbA", "IDOL", "FAKE LOVE ", "작은 것들을 위한 시 (Boy With Luv) ", "Life Goes On ",3,"BTS (방탄소년단)",2019);	addQuestion(q362);
        Question q363 = new Question("1KFQdzSbbKA", "사랑에 연습이 있었다면 (If there was practice in love)", "조금 취했어 (I'm a little drunk) ", "이제 나만 믿어요 (Trust in Me) ", "HERO ",2,"Lim Jae Hyun(임재현)",2019);	addQuestion(q363);
        Question q364 = new Question("GdoNGNe5CSg", "You In Me", "주저하는 연인들을 위해 (for lovers who hesitate) ", "Bomb Bomb (밤밤) ", "가을밤에 든 생각 (A thought on an autumn night) ",2,"JANNABI(잔나비)",2019);	addQuestion(q364);
        Question q365 = new Question("YBnGBb1wg98", "Psycho", "음파음파 (Umpah Umpah) ", "짐살라빔 (Zimzalabim) ", "Queendom ",3,"Red Velvet (레드벨벳)",2019);	addQuestion(q365);
        Question q366 = new Question("1YZzSkP6KZo", "첫 겨울이니까 (First Winter)", "포장마차 (Phocha)", "술이 문제야 (Drunk On Love)", "좋니 (Like it)",1," SUNG SI KYUNG(성시경), IU(아이유)",2019);	addQuestion(q366);
        Question q367 = new Question("2S24-y0Ij3Y", "Ice Cream (with Selena Gomez)", "Lovesick Girls ", "Kill This Love ", "마지막처럼 (AS IF IT'S YOUR LAST) ",3,"BLACKPINK",2019);	addQuestion(q367);
        Question q368 = new Question("8MAYpc7Tjt0", "첫 겨울이니까 (First Winter)", "포장마차 (Phocha) ", "술이 문제야 (Drunk On Love) ", "좋니 (Like it) ",2,"Inwook Hwang(황인욱)",2019);	addQuestion(q368);
        Question q369 = new Question("TbPHPX3hSPA", "여름비 (Summer Rain)", "밤 (Time for the moon night) ", "열대야 (Fever) ", "해야 (Sunrise) ",4,"GFRIEND(여자친구)",2019);	addQuestion(q369);
        Question q370 = new Question("Y99b-ITzPU4", "너를 만나 (Me After You)", "허전해 (empty) ", "Loveship ", "Hangover(너도 아는) ",2,"Paul Kim(폴킴)",2019);	addQuestion(q370);
        Question q371 = new Question("7tO_eJek6D8", "180도 (180 Degree)", "열애중 Love, ing ", "Love Recipe ", "헤어져줘서 고마워 (Thank you for Goodbye) ",4,"벤 (BEN)",2019);	addQuestion(q371);
        Question q372 = new Question("U_dpIqCDcZk", "Spell", "둘 중에 골라 (Summer or Summer) ", "헤어지자(Good bye) ", "water color ",3,"휘인 (Whee In)",2019);	addQuestion(q372);
        Question q373 = new Question("YBEUXfT7_48", "봄이 좋냐?? (What The Spring??)", "당신과는 천천히 (every moment with you) ", "흔들리는 꽃들 속에서 네 샴푸향이 느껴진거야 (Your Shampoo Scent In The Flowers)  ", "폰서트 (Phonecert) ",3,"장범준 (Beom June Jang)",2019);	addQuestion(q373);
        Question q374 = new Question("KhTeiaCezwM", "고고베베 (gogobebe)", "딩가딩가 (Dingga) ", "AYA ", "HIP ",4,"마마무(MAMAMOO)",2019);	addQuestion(q374);
        Question q375 = new Question("tsN-MkpiZB0", "바나나차차 (BANANA CHACHA)", "I'm So Hot ", "뿜뿜 (BBoom Bboom) ", "BAAM ",2,"MOMOLAND (모모랜드)",2019);	addQuestion(q375);
        Question q376 = new Question("Zx58FMoPZEI", "바나나차차 (BANANA CHACHA)", "I'm So Hot ", "뿜뿜 (BBoom Bboom) ", "BAAM ",1,"MOMOLAND (모모랜드)",2019);	addQuestion(q376);
        Question q377 = new Question("Y44vb5I3OAU", "You In Me", "주저하는 연인들을 위해 (for lovers who hesitate) ", "Bomb Bomb (밤밤) ", "가을밤에 든 생각 (A thought on an autumn night) ",3,"KARD",2019);	addQuestion(q377);
        Question q378 = new Question("Zll7O1v63aY", "여름비 (Summer Rain)", "밤 (Time for the moon night) ", "열대야 (Fever) ", "해야 (Sunrise) ",3,"GFRIEND(여자친구)",2019);	addQuestion(q378);
        Question q379 = new Question("CNeNwplE_aw", "누아르 (Noir)", "가시나 (Gashina) ", "꼬리(TAIL) ", "보라빛 밤 (pporappippam) ",1,"선미(SUNMI)",2019);	addQuestion(q379);
        Question q380 = new Question("k6msd9uh8nA", "켜줘 (Light)", "에너제틱 (Energetic) ", "발키리 (Valkyrie) ", "Tell Me ",3,"원어스(ONEUS)",2019);	addQuestion(q380);
        Question q381 = new Question("QTD_yleCK9Y", "BUNGEE (Fall in Love)", "바나나 알러지 원숭이 (Banana allergy monkey) ", "LIAR LIAR ", "Listen to my word(내 얘길 들어봐)(A-ing)",1,"OH MY GIRL (오마이걸)",2019);	addQuestion(q381);
        Question q382 = new Question("ZvhpjERxJlQ", "No", "미 (Me) ", "BLACK DRESS ", "Excuse Me ",1,"CLC",2019);	addQuestion(q382);
        Question q383 = new Question("R3Fwdnij49o", "응응(%%)(Eung Eung)", "시간의 바깥 (above the time) ", "Coin ", "라일락 (LILAC) ",2,"아이유(IU)",2019);	addQuestion(q383);
        Question q384 = new Question("nUOeg1LYF7Y", "빔밤붐 (BIM BAM BUM)", "Ring Ring ", "Why Not ", "PTT (Paint The Town) ",1,"로켓펀치 (Rocket Punch)",2019);	addQuestion(q384);
        Question q385 = new Question("8n9wklIG9qU", "Dancing Cartoon", "품 (Hug) ", "나비와 고양이 (Leo) ", "별 보러 갈래? (Stars over me)",4,"BOL4(볼빨간사춘기)",2019);	addQuestion(q385);
        Question q386 = new Question("3ymwOvzhwHs", "Dance The Night Away", "YES or YES ", "What is Love? ", "Feel Special ",4,"TWICE(트와이스)",2019);	addQuestion(q386);
        Question q387 = new Question("isls26FGUaA", "Destiny (나의 지구)", "종소리 (Twinkle) ", "Obliviate ", "그 시절 우리가 사랑했던 우리(Beautiful Days) ",4,"러블리즈(Lovelyz)",2019);	addQuestion(q387);
        Question q388 = new Question("TbuP2ypiTRc", "Freeze (꼼짝마)", "Wonderful love (어마어마해) ", "Thumbs Up ", "Ready Or Not ",3,"MOMOLAND(모모랜드)",2019);	addQuestion(q388);
        Question q389 = new Question("8xqSz6_RJeU", "Orbit", "마리아 (Maria) ", "I'm Not Cool ", "FLOWER SHOWER ",4,"현아 (HyunA)",2019);	addQuestion(q389);
        Question q390 = new Question("4gX_p1VkgA4", "Adios", "봉봉쇼콜라 (Bon Bon Chocolat) ", "DUN DUN ", "LA DI DA ",1,"EVERGLOW (에버글로우)",2019);	addQuestion(q390);
        Question q391 = new Question("LlQEKB2H7z4", "Trauma", "O Sole Mio(오솔레미오) ", "FLASH ", "해야 해 (Make it) ",3,"X1 (엑스원)",2019);	addQuestion(q391);
        Question q392 = new Question("HvGql8HwOIM", "Adios", "봉봉쇼콜라 (Bon Bon Chocolat) ", "DUN DUN ", "LA DI DA ",2,"EVERGLOW (에버글로우)",2019);	addQuestion(q392);
        Question q393 = new Question("1gQ_-Jp5o7o", "ONE", "WONDERLAND ", "Say My Name ", "After Midnight ",3,"ATEEZ (에이티즈)",2019);	addQuestion(q393);
        Question q394 = new Question("eLfpeg3Fofo", "야간비행 (Turbulence)", "WONDERLAND ", "ONE ", "After Midnight ",2,"ATEEZ (에이티즈)",2019);	addQuestion(q394);
        Question q395 = new Question("eEl1V4UhLTo", "켜줘 (Light)", "안녕하세요 (Begin Again) ", "PARANOIA ", "Sixteen (Feat. Changmo) ",2,"김재환 (KIM JAE HWAN)",2019);	addQuestion(q395);
        Question q396 = new Question("uvs4nyijMZw", "WHY DON’T WE", "최고의 선물 (The Best Present) Prod. By PSY ", "FACE ", "Summer Taste ",3,"김우성 (WooSung)",2019);	addQuestion(q396);
        Question q397 = new Question("aa7hl8A0tAY", "JEALOUSY", "FOLLOW ", "FANTASIA ", "Rush Hour ",2,"MONSTA X (몬스타엑스)",2019);	addQuestion(q397);
        Question q398 = new Question("X-iJZ0gfKPo", "Hello Future", "맛 (Hot Sauce) ", "BOOM ", "RESONANCE ",3,"NCT DREAM (엔시티 드림)",2019);	addQuestion(q398);
        Question q399 = new Question("pAnK1y7qjuE", "Lo Siento (Feat. Leslie Grace)", "House Party ", "Jopping ", "Atlantis ",3,"SuperM (슈퍼엠)",2019);	addQuestion(q399);
        Question q400 = new Question("QOdbtSqv12g", "No", "미 (Me) ", "BLACK DRESS ", "빙글뱅글 (Bingle Bangle) ",2,"CLC",2019);	addQuestion(q400);
        Question q401 = new Question("oDKMLDH8kqc", "MILLIONS", "Remember ", "SOSO ", "아예 (AH YEAH) ",3,"WINNER",2019);	addQuestion(q401);
        Question q402 = new Question("9T_uq_HpfyQ", "You In Me", "주저하는 연인들을 위해 (for lovers who hesitate) ", "Bomb Bomb (밤밤) ", "가을밤에 든 생각 (A thought on an autumn night) ",4,"JANNABI(잔나비)",2020);	addQuestion(q402);
        Question q403 = new Question("L8UUYfe6-UA", "Dancing Cartoon", "품 (Hug) ", "나비와 고양이 (Leo) ", "몰랐니 (Lil' Touch)",3,"BOL4(볼빨간사춘기)",2020);	addQuestion(q403);
        Question q404 = new Question("bho0m505qVA", "내게 들려주고 싶은 말 (Dear Me)", "사계 (Four Seasons) ", "불티 (Spark) ", "Make Me Love You ",1,"TAEYEON (태연)",2020);	addQuestion(q404);
        Question q405 = new Question("tLGHKyZs0Gk", "너에게 난, 나에게 넌 (Me to You, You to Me)", "슈퍼스타 (Superstar) ", "사랑하게 될 줄 알았어 (I Knew I Love) ", "여우야 (Yeowooya) ",1,"미도와 파라솔 (Mido and Falasol)",2020);	addQuestion(q405);
        Question q406 = new Question("vAa8R_ze6ZI", "나빠 (NAPPA)", "놓아줘 (with 태연) ", "둘만의 세상으로 가 (Let Us Go) ", "아무노래 (Any song) ",2,"Crush(크러쉬)",2020);	addQuestion(q406);
        Question q407 = new Question("tJQaUW36pMw", "마리아 (Maria)", "Cold Blooded ", "눈누난나 (NUNU NANA) ", "Gucci ",3,"Jessi (제시)",2020);	addQuestion(q407);
        Question q408 = new Question("FFmdTU4Cpr8", "Bye bye my blue", "니 소식 (Your regards) ", "다시 난, 여기 (Here I Am Again) ", "쏘쏘(SO-SO)",3,"백예린 (Yerin Baek)",2020);	addQuestion(q408);
        Question q409 = new Question("ESKfHHtiSjs", "DON'T TOUCH ME", "Shut Up ", "다시 여기 바닷가(Beach Again) ", "몰랐니 (Lil' Touch)",3,"싹쓰리(SSAK3)",2020);	addQuestion(q409);
        Question q410 = new Question("WqzTRK5GPWQ", "FIVE", "너 그리고 나 (NAVILLERA) ", "밤 (Time for the moon night) ", "덤더럼(Dumhdurum) ",4,"Apink(에이핑크)",2020);	addQuestion(q410);
        Question q411 = new Question("PXE2Ykf8fXQ", "DON'T TOUCH ME", "Shut Up ", "다시 여기 바닷가(Beach Again) ", "몰랐니 (Lil' Touch)",1,"REFUND SISTERS(환불원정대)",2020);	addQuestion(q411);
        Question q412 = new Question("oaRTMjLdiDw", "컬러링북 (Coloring Book)", "HAPPY ", "Dolphin ", "살짝 설렜어 (Nonstop) ",3,"OH MY GIRL(오마이걸)",2020);	addQuestion(q412);
        Question q413 = new Question("EXJfWc4JciQ", "나빠 (NAPPA)", "놓아줘 (with 태연) ", "둘만의 세상으로 가 (Let Us Go) ", "아무노래 (Any song) ",3,"Crush(크러쉬)",2020);	addQuestion(q413);
        Question q414 = new Question("dfl9KIX1WpU", "고고베베 (gogobebe)", "딩가딩가 (Dingga) ", "AYA ", "WANNA BE MYSELF ",2,"마마무 (MAMAMOO)",2020);	addQuestion(q414);
        Question q415 = new Question("cO9DbwTF7rY", "살짝 설렜어 (Nonstop)", "LALALILALA ", "Crazy ", "Dolphin ",2,"APRIL(에이프릴)",2020);	addQuestion(q415);
        Question q416 = new Question("-5q5mZbe3V8", "IDOL", "FAKE LOVE ", "작은 것들을 위한 시 (Boy With Luv) ", "Life Goes On ",4,"BTS (방탄소년단)",2020);	addQuestion(q416);
        Question q417 = new Question("dM5gMsePq-o", "Loveship", "허전해 (empty) ", "우리 만남이 (But I'll Miss You) ", "Hangover(너도 아는) ",1,"Paul Kim(폴킴), CHUNG HA(청하)",2020);	addQuestion(q417);
        Question q418 = new Question("dyRsYk0LyA8", "Ice Cream (with Selena Gomez)", "Lovesick Girls ", "Kill This Love ", "마지막처럼 (AS IF IT'S YOUR LAST) ",2,"BLACKPINK",2020);	addQuestion(q418);
        Question q419 = new Question("brZRDjFIFJs", "Orbit", "마리아 (Maria) ", "I'm Not Cool ", "FLOWER SHOWER ",2,"화사 (Hwa Sa)",2020);	addQuestion(q419);
        Question q420 = new Question("mH0_XpSHkZo", "SCIENTIST", "Alcohol-Free", "I CAN'T STOP ME ", "MORE & MORE ",4,"TWICE(트와이스)",2020);	addQuestion(q420);
        Question q421 = new Question("CQ7p6kYal5A", "밤하늘의 저 별처럼 (Midnight)", "가끔 이러다 (Sometimes) ", "Stay With Me ", "Ring Ring ",1,"Heize(헤이즈), Punch(펀치)",2020);	addQuestion(q421);
        Question q422 = new Question("Is7glC9Jp7Q", "누아르 (Noir)", "가시나 (Gashina) ", "꼬리(TAIL) ", "보라빛 밤 (pporappippam) ",4,"선미 (SUNMI)",2020);	addQuestion(q422);
        Question q423 = new Question("9OADFEl-QQ0", "거짓말 (Lie) (Feat. 이해리 of 다비치)", "Lost Without You(우리집을 못 찾겠군요) (feat.Bolbbalgan4(볼빨간사춘기)) ", "보자보자 (Let's see) ", "Counting Stars (feat. Beenzino) ",3,"머쉬베놈 (MUSHVENOM)",2020);	addQuestion(q423);
        Question q424 = new Question("rOCymN-Rwiw", "너에게 난, 나에게 넌 (Me to You, You to Me)", "슈퍼스타 (Superstar) ", "사랑하게 될 줄 알았어 (I Knew I Love) ", "여우야 (Yeowooya) ",3,"전미도 (JEON MI DO)",2020);	addQuestion(q424);
        Question q425 = new Question("Ygrv55VRRas", "Rose", "음 (Mmmh) ", "사랑, 하자 (Let’s Love) ", "괜찮아도 괜찮아 (That's okay) ",3,"SUHO 수호",2020);	addQuestion(q425);
        Question q426 = new Question("iDjQSdN_ig8", "HAPPY", "다섯 번째 계절 (The fifth season) ", "Dolphin ", "살짝 설렜어 (Nonstop) ",4,"OH MY GIRL(오마이걸)",2020);	addQuestion(q426);
        Question q427 = new Question("AAOOKbk-knM", "BUNGEE (Fall in Love)", "바나나 알러지 원숭이 (Banana allergy monkey) ", "LIAR LIAR ", "숲의 아이 (Bon voyage) ",4,"유아(YooA)",2020);	addQuestion(q427);
        Question q428 = new Question("YPFIh0dfYfw", "Roller Coaster", "Snapping ", "그대라는 시 (All about you) ", "Stay Tonight ",4,"청하 (CHUNG HA)",2020);	addQuestion(q428);
        Question q429 = new Question("BfWqUjunXXU", "그날처럼 (Good old days)", "당신과는 천천히 (every moment with you) ", "흔들리는 꽃들 속에서 네 샴푸향이 느껴진거야 (Your Shampoo Scent In The Flowers)  ", "실버판테온 (SILVERPantheon) ",4,"장범준 (Beom June Jang)",2020);	addQuestion(q429);
        Question q430 = new Question("oKUEbsJDvuo", "She's a Baby", "SoulMate ", "Summer Hate (Feat. Rain(비)) ", "아무노래 (Any song) ",3,"지코 (ZICO)",2020);	addQuestion(q430);
        Question q431 = new Question("3DOkxQ3HDXE", "아로하 (Aloha)", "좋아좋아 (I Like You) ", "가을 타나 봐 (Fall in Fall) ", "비와 당신 (Rain and You) ",1,"조정석 (CHO JUNG SEOK)",2020);	addQuestion(q431);
        Question q432 = new Question("UuV2BmJ1p_I", "She's a Baby", "SoulMate ", "Summer Hate (Feat. Rain(비)) ", "아무노래 (Any song) ",4,"지코 (ZICO)",2020);	addQuestion(q432);
        Question q433 = new Question("vRXZj0DzXIA", "Ice Cream (with Selena Gomez)", "Lovesick Girls ", "Kill This Love ", "마지막처럼 (AS IF IT'S YOUR LAST) ",1,"BLACKPINK",2020);	addQuestion(q433);
        Question q434 = new Question("CM4CkVFmTds", "SCIENTIST", "Alcohol-Free", "I CAN'T STOP ME ", "MORE & MORE ",3,"TWICE(트와이스)",2020);	addQuestion(q434);
        Question q435 = new Question("x_JeRI2eK9o", "하늘바라기 (Hopefully sky)", "내가 설렐 수 있게 (Only one) ", "AWay ", "NoNoNo ",3,"Jeong Eun Ji(정은지)",2020);	addQuestion(q435);
        Question q436 = new Question("TgOu00Mf3kI", "strawberry moon", "에잇 (eight) ", "블루밍 (Blueming) ", "Celebrity ",2,"IU(아이유)",2020);	addQuestion(q436);
        Question q437 = new Question("om3n2ni8luE", "화(火花)(HWAA)", "Oh my god", "LA DI DA", "DUN DUN",2,"(여자)아이들((G)I-DLE)",2020);	addQuestion(q437);
        Question q438 = new Question("mPVDGOVjRQ0", "예쁘다 (Pretty U)", "붐붐 (BOOMBOOM) ", "ON ", "피 땀 눈물 (Blood Sweat & Tears) ",3,"BTS (방탄소년단)",2020);	addQuestion(q438);
        Question q439 = new Question("lBYyAQ99ZFI", "Birthday", "What You Waiting For ", "DUMB DUMB ", "XOXO ",2,"JEON SOMI (전소미)",2020);	addQuestion(q439);
        Question q440 = new Question("FEI_imQ1Eaw", "너를 만나 (Me After You)", "Loveship ", "우리 만남이 (But I'll Miss You) ", "Hangover(너도 아는) ",3,"Paul Kim(폴킴)",2020);	addQuestion(q440);
        Question q441 = new Question("fE2h3lGlOsk", "달라달라(DALLA DALLA)", "ICY ", "WANNABE ", "Not Shy ",3,"ITZY(있지)",2020);	addQuestion(q441);
        Question q442 = new Question("zrsBjYukE8s", "New Face", "When We Disco (Duet with 선미) ", "I LUV IT ", "해야 해 (Make it) ",2,"박진영 (J.Y. Park)",2020);	addQuestion(q442);
        Question q443 = new Question("sLqZxb4xjJw", "사랑에 연습이 있었다면 (If there was practice in love)", "조금 취했어 (I'm a little drunk) ", "이제 나만 믿어요 (Trust in Me) ", "HERO ",3,"Lim Young Woong(임영웅)",2020);	addQuestion(q443);
        Question q444 = new Question("k8gx-C7GCGU", "꽃이야 (Myflower)", "부를게 (Call Your Name) ", "뚫고 지나가요 (Right Through Me) ", "Zombie ",4,"DAY6(데이식스)",2020);	addQuestion(q444);
        Question q445 = new Question("hoLzH1revMg", "여우야 (Yeowooya)", "괜찮아, 난 (I'm OK) (Feat. 이현우 (Lee Hyun Woo)) ", "좋은 사람 있으면 소개시켜줘 (Introduce me a good person) ", "안녕 (Hello) ",3,"조이 (JOY)",2020);	addQuestion(q445);
        Question q446 = new Question("3XUe2PsadEk", "어디에도 (No matter where)", "사랑이 식었다고 말해도 돼 (My love has faded away) ", "처음처럼 (BLOOM) ", "그대라는 사치 (Amazing You) ",3,"M.C THE MAX(엠씨더맥스)",2020);	addQuestion(q446);
        Question q447 = new Question("d7W56dwrTlE", "살짝 설렜어 (Nonstop)", "LALALILALA ", "Crazy ", "Dolphin ",3,"APRIL(에이프릴)",2020);	addQuestion(q447);
        Question q448 = new Question("qfeoX17dav0", "Dancing Cartoon", "품 (Hug) ", "나비와 고양이 (Leo) ", "피카부 (Peek-A-Boo)",2,"BOL4(볼빨간사춘기)",2020);	addQuestion(q448);
        Question q449 = new Question("eDEFolvLn0A", "Panorama", "환상동화 (Secret Story of the Swan) ", "FIESTA ", "비올레타 (Violeta) ",3,"IZ*ONE (아이즈원)",2020);	addQuestion(q449);
        Question q450 = new Question("ioNng23DkIM", "Ice Cream (with Selena Gomez)", "Lovesick Girls ", "How You Like That ", "마지막처럼 (AS IF IT'S YOUR LAST) ",3,"BLACKPINK",2020);	addQuestion(q450);
        Question q451 = new Question("VdeK_VsG9U0", "홀로 (HOLO)", "소리 (Sori) ", "We All Lie ", "니 소식 (Your regards) ",1,"이하이 (LEE HI)",2020);	addQuestion(q451);
        Question q452 = new Question("nnVjsos40qk", "Panorama", "환상동화 (Secret Story of the Swan) ", "FIESTA ", "비올레타 (Violeta) ",2,"IZ*ONE (아이즈원)",2020);	addQuestion(q452);
        Question q453 = new Question("zRq_DlEzygk", "염라(Karma)", "좋아해 (Love You) ", "너로피어오라(Flowering) ", "Today's Mood(오늘의 기분) ",4,"CHEEZE(치즈)",2020);	addQuestion(q453);
        Question q454 = new Question("5cZ4EDEZ0XQ", "잘할게 (I will)", "미안해 (lie) ", "뻔한남자 (The Ordinary Man) ", "Darling ",1,"이승기 (LEE SEUNG GI)",2020);	addQuestion(q454);
        Question q455 = new Question("wUUiBCput2E", "Spell", "둘 중에 골라 (Summer or Summer) ", "헤어지자(Good bye) ", "water color ",1,"효린 (Hyolyn)",2020);	addQuestion(q455);
        Question q456 = new Question("M33FPNp8-XE", "Bad Girl", "WEE WOO ", "WE LIKE ", "유리구두 (Glass Shoes) ",1,"woo!ah!(우아!)",2020);	addQuestion(q456);
        Question q457 = new Question("SUKQpVGnKAM", "잘할게 (I will)", "미안해 (lie) ", "뻔한남자 (The Ordinary Man) ", "Darling ",4,"양다일 (Yang Da Il)",2020);	addQuestion(q457);
        Question q458 = new Question("jv543Nk5s18", "염라(Karma)", "좋아해 (Love You) ", "너로피어오라(Flowering) ", "Today's Mood(오늘의 기분) ",1,"달의하루(Dareharu)",2020);	addQuestion(q458);
        Question q459 = new Question("3vhA8njtoQg", "염라(Karma)", "좋아해 (Love You) ", "너로피어오라(Flowering) ", "Today's Mood(오늘의 기분) ",3,"달의하루(Dareharu)",2020);	addQuestion(q459);
        Question q460 = new Question("b-kGspYdntY", "이별하러 가는 길 (The Way To Say Goodbye)", "내가 저지른 사랑(The love I committed) ", "하루도 그대를 사랑하지 않은 적이 없었다(There has never been a day I haven't loved you) ", "이 노래가 뭐라고 (This Song) ",4,"임한별 (Onestar)",2020);	addQuestion(q460);
        Question q461 = new Question("LQOifePx_XQ", "RADIO", "톰보이 (TOMBOY) ", "True Love ", "이 노래가 클럽에서 나온다면 (Fire up)  ",1,"HENRY(헨리)",2020);	addQuestion(q461);
        Question q462 = new Question("uJEKfFUbu0c", "잘할게 (I will)", "홀로 (HOLO) ", "소리 (Sori) ", "Darling ",3,"이승기 (LEE SEUNG GI)",2020);	addQuestion(q462);
        Question q463 = new Question("NuTNPV72rFo", "비가 내리는 날에는 (On A Rainy Day)", "My Love ", "별의 조각 (Stardust) ", "밤하늘의 별을 (Shiny Star) ",4,"경서 (KyoungSeo)",2020);	addQuestion(q463);
        Question q464 = new Question("lcRV2gyEfMo", "PTT (Paint The Town)", "ASAP ", "색안경 (STEREOTYPE) ", "SO BAD ",4,"STAYC(스테이씨)",2020);	addQuestion(q464);
        Question q465 = new Question("ljdwsoYTq7I", "Got That Boom", "I Like That ", "Who Dis? ", "LONELY ",1,"시크릿넘버 (SECRET NUMBER)",2020);	addQuestion(q465);
        Question q466 = new Question("ZcwORBWKl-U", "Dancing Cartoon", "품 (Hug) ", "나비와 고양이 (Leo) ", "Listen to my word(내 얘길 들어봐)(A-ing)",1,"BOL4(볼빨간사춘기)",2020);	addQuestion(q466);
        Question q467 = new Question("NEAwZaYIQ4A", "사랑에 연습이 있었다면 (If there was practice in love)", "조금 취했어 (I'm a little drunk) ", "이제 나만 믿어요 (Trust in Me) ", "HERO ",4,"Lim Young Woong(임영웅)",2020);	addQuestion(q467);
        Question q468 = new Question("jD_qe-ew-O0", "고고베베 (gogobebe)", "딩가딩가 (Dingga) ", "AYA ", "WANNA BE MYSELF ",3,"마마무 (MAMAMOO)",2020);	addQuestion(q468);
        Question q469 = new Question("UOCPm8qa0Lc", "DRAMARAMA", "아름다워(Beautiful) ", "Love Killa ", "Shall We Dance ",3,"몬스타엑스(MONSTA X)",2020);	addQuestion(q469);
        Question q470 = new Question("Ktmd_xTQaU8", "기다렸다 가 (nosedive)", "옥탑방 (Rooftop) ", "Pump It Up ", "Nostalgia ",4,"DRIPPIN(드리핀)",2020);	addQuestion(q470);
        Question q471 = new Question("3QDIjIZPSiM", "Loveship", "허전해 (empty) ", "우리 만남이 (But I'll Miss You) ", "Hangover(너도 아는) ",4,"Paul Kim(폴킴)",2020);	addQuestion(q471);
        Question q472 = new Question("0-snXdhDs1w", "빔밤붐 (BIM BAM BUM)", "Ring Ring ", "Why Not ", "PTT (Paint The Town) ",3,"LOONA (이달의 소녀)",2020);	addQuestion(q472);
        Question q473 = new Question("1FqAoADnId4", "Tag Me (@Me)", "Holiday Party ", "Zig Zag ", "After School ",3,"Weeekly(위클리)",2020);	addQuestion(q473);
        Question q474 = new Question("nE3jUnYNFds", "기다렸다 가 (nosedive)", "옥탑방 (Rooftop) ", "Pump It Up ", "Nostalgia ",3,"Golden Child(골든차일드)",2020);	addQuestion(q474);
        Question q475 = new Question("2swgQFESu9A", "Sorry", "BABY ", "RETRO FUTURE ", "TWILIGHT ",4,"WEi(위아이)",2020);	addQuestion(q475);
        Question q476 = new Question("IBMiaPKJCeI", "The Stealer", "THRILL RIDE ", "No Air ", "Giddy Up ",1,"THE BOYZ(더보이즈)",2020);	addQuestion(q476);
        Question q477 = new Question("KBuvtmMfdN8", "고고베베 (gogobebe)", "딩가딩가 (Dingga) ", "AYA ", "WANNA BE MYSELF ",4,"마마무 (MAMAMOO)",2020);	addQuestion(q477);
        Question q478 = new Question("9GUqqRzIZgw", "Destiny (나의 지구)", "종소리 (Twinkle) ", "Obliviate ", "그 시절 우리가 사랑했던 우리(Beautiful Days) ",3,"러블리즈(Lovelyz)",2020);	addQuestion(q478);
        Question q479 = new Question("G8GaQdW2wHc", "Panorama", "환상동화 (Secret Story of the Swan) ", "FIESTA ", "비올레타 (Violeta) ",1,"IZ*ONE (아이즈원)",2020);	addQuestion(q479);
        Question q480 = new Question("mkYwq_CKpyw", "Got That Boom", "I Like That ", "Who Dis? ", "LONELY ",3,"시크릿넘버 (SECRET NUMBER)",2020);	addQuestion(q480);
        Question q481 = new Question("Moq0aOiTUOA", "Tag Me (@Me)", "Holiday Party ", "Zig Zag ", "After School ",1,"Weekly(위클리)",2020);	addQuestion(q481);
        Question q482 = new Question("wTowEKjDGkU", "달라달라(DALLA DALLA)", "ICY ", "WANNABE ", "Not Shy ",4,"ITZY(있지)",2020);	addQuestion(q482);
        Question q483 = new Question("ZeerrnuLi5E", "Not Shy", "Savage", "Next Level", "Black Mamba",4,"aespa (에스파)",2020);	addQuestion(q483);
        Question q484 = new Question("NTJ8esMHW2E", "MILLIONS", "Remember ", "SOSO ", "아예 (AH YEAH) ",2,"WINNER",2020);	addQuestion(q484);
        Question q485 = new Question("1eWm7NwjGco", "몸 (BODY)", "도망가 (Run away) ", "탕!♡ (TANG!♡) ", "아낙네 (FIANCÉ) ",2,"MINO",2020);	addQuestion(q485);
        Question q486 = new Question("MsNH0qBoJQs", "Freeze (꼼짝마)", "Wonderful love (어마어마해) ", "Thumbs Up ", "Ready Or Not ",4,"MOMOLAND(모모랜드)",2020);	addQuestion(q486);
        Question q487 = new Question("NoYKBAajoyo", "Adios", "봉봉쇼콜라 (Bon Bon Chocolat) ", "DUN DUN ", "LA DI DA ",3,"EVERGLOW (에버글로우)",2020);	addQuestion(q487);
        Question q488 = new Question("jeI992mvlEY", "Adios", "봉봉쇼콜라 (Bon Bon Chocolat) ", "DUN DUN ", "LA DI DA ",4,"EVERGLOW (에버글로우)",2020);	addQuestion(q488);
        Question q489 = new Question("NhnDJLx1im8", "Orbit", "마리아 (Maria) ", "I'm Not Cool ", "FLOWER SHOWER ",1,"화사 (Hwa sa)",2020);	addQuestion(q489);
        Question q490 = new Question("HaqhQs5lBWE", "파랗게 (Love Me Harder)", "BUMP BUMP ", "FEEL LIKE ", "WAITING ",1,"WOODZ (조승연)",2020);	addQuestion(q490);
        Question q491 = new Question("h1ee4sPdaLI", "파랗게 (Love Me Harder)", "BUMP BUMP ", "FEEL LIKE ", "WAITING ",2,"WOODZ (조승연)",2020);	addQuestion(q491);
        Question q492 = new Question("I1ISdt6Jfp8", "비가 내리는 날에는 (On A Rainy Day)", "My Love ", "별의 조각 (Stardust) ", "밤하늘의 별을 (Shiny Star) ",2,"거미 (GUMMY)",2020);	addQuestion(q492);
        Question q493 = new Question("rQpWdm5zHOU", "What You Waiting For", "DUMB DUMB ", "XOXO ", "NINETEEN ",4,"나띠 (NATTY)",2020);	addQuestion(q493);
        Question q494 = new Question("p2JzPlpA1Iw", "JEALOUSY", "FOLLOW ", "FANTASIA ", "Rush Hour ",3,"MONSTA X (몬스타엑스)",2020);	addQuestion(q494);
        Question q495 = new Question("ftyLwZ68LnU", "BUTTERFLY", "Easy (이지) ", "HAPPY ", "비밀이야 (Secret) ",1,"우주소녀 (WJSN)",2020);	addQuestion(q495);
        Question q496 = new Question("tyrVtwE8Gv0", "일곱 번째 감각 (The 7th Sense)", "BOSS ", "Baby Don't Stop ", "Make A Wish (Birthday Song) ",4,"NCT U (엔시티 유)",2020);	addQuestion(q496);
        Question q497 = new Question("0IpbvXVbBYA", "Hello Future", "맛 (Hot Sauce) ", "BOOM ", "RESONANCE ",4,"NCT 2020 (엔시티 2020)",2020);	addQuestion(q497);
        Question q498 = new Question("Fc-fa6cAe2c", "Rose", "음 (Mmmh) ", "사랑, 하자 (Let’s Love) ", "괜찮아도 괜찮아 (That's okay) ",2,"KAI (카이)",2020);	addQuestion(q498);
        Question q499 = new Question("4HjcypoChfQ", "운전만해 (We Ride)", "치맛바람 (Chi Mat Ba Ram) ", "Summer Taste ", "BAAM ",1,"브레이브걸스 (Brave Girls)",2020);	addQuestion(q499);
        Question q500 = new Question("gdZLi9oWNZg", "Butter", "Dynamite ", "Permission to Dance ", "소리꾼 (Thunderous) ",2,"BTS (방탄소년단)",2020);	addQuestion(q500);
        Question q501 = new Question("Ujb-gvqsoi0", "Psycho", "음파음파 (Umpah Umpah) ", "Monster ", "짐살라빔 (Zimzalabim) ",3,"Red Velvet - IRENE & SEULGI",2020);	addQuestion(q501);
        Question q502 = new Question("rVXeArOQIs4", "너는 어땠을까 (How about you)", "어떻게 지내 (I Need You) ", "all of my life ", "늦은 밤 너의 집 앞 골목길에서 (Late Night) ",2,"OVAN(오반)",2020);	addQuestion(q502);
        Question q503 = new Question("XZSv3aMGg5Q", "Christmas EveL", "Hellevator ", "Easy ", "소리꾼 (Thunderous) ",3,"Stray Kids(스트레이 키즈)",2020);	addQuestion(q503);
        Question q504 = new Question("WHkQtNBCRQo", "아로하 (Aloha)", "좋아좋아 (I Like You) ", "가을 타나 봐 (Fall in Fall) ", "비와 당신 (Rain and You) ",3,"이무진 (Lee Mujin)",2021);	addQuestion(q504);
        Question q505 = new Question("X3PFu82F_S8", "당신과는 천천히 (every moment with you)", "폰서트 (Phonecert)", "그러나 (however)", "고백 (Go Back)",4,"10cm",2021);	addQuestion(q505);
        Question q506 = new Question("RIMZ0pZh2uk", "과제곡 (The Assignment Song)", "Traffic light(신호등) ", "좋은 날 (Good Day) ", "It's you (Feat.ZICO) ",1,"Lee Mujin(이무진)",2021);	addQuestion(q506);
        Question q507 = new Question("ORYgMXQ8Kqg", "그냥 안아달란 말야 (Just hug me)", "너에게 못했던 내 마지막 말은 (Unspoken Words) ", "내 옆에 그대인걸 (Beside me) ", "하늘바라기 (Hopefully sky) ",1,"다비치 (DAVICHI)",2021);	addQuestion(q507);
        Question q508 = new Question("Aui0ZKIaXVc", "누아르 (Noir)", "가시나 (Gashina) ", "꼬리(TAIL) ", "보라빛 밤 (pporappippam) ",3,"선미 (SUNMI)",2021);	addQuestion(q508);
        Question q509 = new Question("JrWQdHjOkvw", "나비효과 (Butterfly Effect)", "남이 될 수 있을까(We Loved) ", "소리 (Sori) ", "Listen to my word(내 얘길 들어봐)(A-ing)",1,"BOL4(볼빨간사춘기)",2021);	addQuestion(q509);
        Question q510 = new Question("EtiPbWzUY9o", "우아해 (woo ah)", "DINOSAUR", "선물을 고르며 (A Gift!)", "낙하 (NAKKA) (with IU) ",4,"AKMU",2021);	addQuestion(q510);
        Question q511 = new Question("ZcnnUoyv-hs", "Face ID", "Rosario ", "내 얘기 같아 (Based On A True Story) ", "술이 달다 (LOVEDRUNK) ",3,"EPIK HIGH (에픽하이)",2021);	addQuestion(q511);
        Question q512 = new Question("4TWR90KJl84", "Not Shy", "Savage", "Next Level", "Black Mamba",3,"aespa (에스파)",2021);	addQuestion(q512);
        Question q513 = new Question("HzOjwL7IP_o", "LIAR LIAR", "HAPPY ", "Dun Dun Dance ", "컬러링북 (Coloring Book) ",3,"오마이걸(OH MY GIRL)",2021);	addQuestion(q513);
        Question q514 = new Question("tg2uF3R_Ozo", "Birthday", "What You Waiting For ", "DUMB DUMB ", "XOXO ",3,"JEON SOMI (전소미)",2021);	addQuestion(q514);
        Question q515 = new Question("p6OoY6xneI0", "Atlantis", "1 of 1 ", "Don't Call Me ", "Lo Siento (Feat. Leslie Grace) ",3,"SHINee (샤이니)",2021);	addQuestion(q515);
        Question q516 = new Question("2IkoKhr6Tss", "Tempo", "Obsession ", "Don't fight the feeling ", "Ko Ko Bop ",3,"EXO (엑소)",2021);	addQuestion(q516);
        Question q517 = new Question("nBxnh010LU8", "Spell", "둘 중에 골라 (Summer or Summer) ", "헤어지자(Good bye) ", "water color ",2,"효린, 다솜 (HYOLYN, DASOM)",2021);	addQuestion(q517);
        Question q518 = new Question("N2p__-LRBNc", "꽃이야 (Myflower)", "부를게 (Call Your Name) ", "뚫고 지나가요 (Right Through Me) ", "Zombie ",3,"DAY6(데이식스)",2021);	addQuestion(q518);
        Question q519 = new Question("awkkyBH2zEo", "SOLO", "LALISA ", "On The Ground ", "마지막처럼 (AS IF IT'S YOUR LAST) ",2,"LISA",2021);	addQuestion(q519);
        Question q520 = new Question("v7bnOxV4jAc", "On the Ground", "strawberry moon ", "Coin ", "라일락 (LILAC) ",4,"IU(아이유)",2021);	addQuestion(q520);
        Question q521 = new Question("WpuatuzSDK4", "아주 NICE (VERY NICE)", "CLAP ", "울고 싶지 않아 (Don't Wanna Cry) ", "Rock with you ",4,"SEVENTEEN (세븐틴)",2021);	addQuestion(q521);
        Question q522 = new Question("yCvSR4lSqTg", "울고 싶지 않아 (Don't Wanna Cry)", "Rock with you ", "여전히 아름다운지 (Is It Still Beautiful) ", "Ready to love ",4,"SEVENTEEN (세븐틴)",2021);	addQuestion(q522);
        Question q523 = new Question("MjCZfZfucEc", "SWIPE", "마.피.아. In the morning ", "LOCO ", "비올레타 (Violeta) ",3,"ITZY(있지)",2021);	addQuestion(q523);
        Question q524 = new Question("FCsLikmxhV0", "Face ID", "Rosario ", "내 얘기 같아 (Based On A True Story) ", "술이 달다 (LOVEDRUNK) ",2,"EPIK HIGH (에픽하이)",2021);	addQuestion(q524);
        Question q525 = new Question("_btxV8tJM7w", "Rose", "음 (Mmmh) ", "사랑, 하자 (Let’s Love) ", "괜찮아도 괜찮아 (That's okay) ",1,"D.O. (디오)",2021);	addQuestion(q525);
        Question q526 = new Question("G_SmwQ06TQE", "빔밤붐 (BIM BAM BUM)", "Ring Ring ", "Why Not ", "PTT (Paint The Town) ",2,"로켓펀치(Rocket Punch)",2021);	addQuestion(q526);
        Question q527 = new Question("_ysomCGaZLw", "SWIPE", "마.피.아. In the morning ", "LOCO ", "비올레타 (Violeta) ",2,"ITZY(있지)",2021);	addQuestion(q527);
        Question q528 = new Question("PkKnp4SdE-w", "Hello Future", "맛 (Hot Sauce) ", "BOOM ", "RESONANCE ",2,"NCT DREAM (엔시티 드림)",2021);	addQuestion(q528);
        Question q529 = new Question("8M3WUaeIbOk", "Bambi", "UN Village ", "비가와 (Rain) ", "Dream ",1,"BAEKHYUN (백현)",2021);	addQuestion(q529);
        Question q530 = new Question("WMweEpGlu_U", "Butter", "Dynamite ", "Permission to Dance ", "Hey - Mama!",1,"BTS (방탄소년단)",2021);	addQuestion(q530);
        Question q531 = new Question("OYWWnd9ACMI", "BAD LOVE", "WANT ", "MOVE ", "Press Your Number ",1,"KEY (키)",2021);	addQuestion(q531);
        Question q532 = new Question("5Kdl9uOmj34", "CALLING YOU", "불어온다 (NOT THE END) ", "사랑했나봐 (Loved) ", "걸어 (ALL IN) ",2,"Highlight(하이라이트)",2021);	addQuestion(q532);
        Question q533 = new Question("fOdML_XhHvQ", "We don't talk together", "비가 오는 날엔 (2021)(On Rainy Day) ", "헤픈 우연 (HAPPEN) ", "Jenga (Feat. Gaeko) ",2,"헤이즈 (Heize)",2021);	addQuestion(q533);
        Question q534 = new Question("sCmcSBsTxQc", "아로하 (Aloha)", "좋아좋아 (I Like You) ", "가을 타나 봐 (Fall in Fall) ", "비와 당신 (Rain and You) ",4,"이무진 (Lee Mujin)",2021);	addQuestion(q534);
        Question q535 = new Question("PEKkdIT8JPM", "겨울동화 (Winter Story)", "YOU AND I ", " 휘휘 (Hwi hwi) ", "BEcause ",4,"Dreamcatcher(드림캐쳐)",2021);	addQuestion(q535);
        Question q536 = new Question("WPdWvnAAurg", "Not Shy", "Savage", "Next Level", "Black Mamba",2,"aespa (에스파)",2021);	addQuestion(q536);
        Question q537 = new Question("Xmxcnf2v_gs", "PTT (Paint The Town)", "ASAP ", "색안경 (STEREOTYPE) ", "SO BAD ",3,"STAYC(스테이씨)",2021);	addQuestion(q537);
        Question q538 = new Question("0-q1KafFCLU", "Celebrity", "strawberry moon ", "Coin ", "라일락 (LILAC) ",1,"IU(아이유)",2021);	addQuestion(q538);
        Question q539 = new Question("EaswWiwMVs8", "Christmas EveL", "Hellevator ", "Easy ", "소리꾼 (Thunderous) ",4,"Stray Kids(스트레이 키즈)",2021);	addQuestion(q539);
        Question q540 = new Question("XMs2CIiqRDI", "The Stealer", "THRILL RIDE ", "No Air ", "Giddy Up ",2,"THE BOYZ(더보이즈)",2021);	addQuestion(q540);
        Question q541 = new Question("sqgxcCjD04s", "On the Ground", "strawberry moon ", "Coin ", "라일락 (LILAC) ",2,"IU(아이유)",2021);	addQuestion(q541);
        Question q542 = new Question("1oYWnbTSang", "Cherry Bomb", "Superhuman ", "Sticker ", "Favorite (Vampire) ",3,"NCT 127 (엔시티 127)",2021);	addQuestion(q542);
        Question q543 = new Question("QMwJtMJLXE0", "봄(Spring)", "SPICY ", "No ", "미 (Me) ",2,"CL",2021);	addQuestion(q543);
        Question q544 = new Question("SK6Sm2Ki9tI", "과제곡 (The Assignment Song)", "Traffic light(신호등) ", "좋은 날 (Good Day) ", "It's you (Feat.ZICO) ",2,"Lee Mujin(이무진)",2021);	addQuestion(q544);
        Question q545 = new Question("BUjI4XPPfh0", "WHY DON’T WE", "최고의 선물 (The Best Present) Prod. By PSY ", "FACE ", "Summer Taste ",4,"RAIN,MONSTA X,BraveGirls,ATEEZ(비,몬스타엑스,브레이브걸스,에이티즈)",2021);	addQuestion(q545);
        Question q546 = new Question("_yXEnhyOTQo", "Orbit", "마리아 (Maria) ", "I'm Not Cool ", "FLOWER SHOWER ",3,"현아 (HyunA)",2021);	addQuestion(q546);
        Question q547 = new Question("PSYRbJjIT6U", "Atlantis", "1 of 1 ", "Don't Call Me ", "Lo Siento (Feat. Leslie Grace) ",1,"SHINee (샤이니)",2021);	addQuestion(q547);
        Question q548 = new Question("sfdJRW5NGyA", "에너제틱 (Energetic)", "PARANOIA ", "Antidote ", "뭐해 (What are you up to) ",3,"강다니엘(KANGDANIEL)",2021);	addQuestion(q548);
        Question q549 = new Question("XA2YEHn-A8Q", "SCIENTIST", "Alcohol-Free", "I CAN'T STOP ME ", "MORE & MORE ",2,"TWICE(트와이스)",2021);	addQuestion(q549);
        Question q550 = new Question("32kd8oFHoAw", "야간비행 (Turbulence)", "WONDERLAND ", "ONE ", "After Midnight ",4,"ASTRO(아스트로)",2021);	addQuestion(q550);
        Question q551 = new Question("FjbCwQ7hliw", "BUTTERFLY", "너에게 닿기를 (I Wish) ", "HAPPY ", "UNNATURAL ",4,"WJSN(우주소녀)",2021);	addQuestion(q551);
        Question q552 = new Question("NsY-9MCOIAQ", "PTT (Paint The Town)", "ASAP ", "색안경 (STEREOTYPE) ", "SO BAD ",2,"STAYC(스테이씨)",2021);	addQuestion(q552);
        Question q553 = new Question("ck538udT0b8", "울고 싶지 않아 (Don't Wanna Cry)", "Rock with you ", "여전히 아름다운지 (Is It Still Beautiful) ", "Ready to love ",3,"SEVENTEEN (세븐틴)",2021);	addQuestion(q553);
        Question q554 = new Question("CKZvWhCqx1s", "SOLO", "LALISA ", "On The Ground ", "마지막처럼 (AS IF IT'S YOUR LAST) ",3,"ROSE",2021);	addQuestion(q554);
        Question q555 = new Question("srWfDwiRVgQ", "WHY DON’T WE", "최고의 선물 (The Best Present) Prod. By PSY ", "FACE ", "Summer Taste ",1,"RAIN (비)",2021);	addQuestion(q555);
        Question q556 = new Question("DslHQto2V7I", "사랑을 했다(LOVE SCENARIO)", "이별길(GOODBYE ROAD) ", "왜왜왜 (Why Why Why) ", "아예 (AH YEAH) ",3,"iKON",2021);	addQuestion(q556);
        Question q557 = new Question("ShFKF2YN7H0", "Spell", "둘 중에 골라 (Summer or Summer) ", "헤어지자(Good bye) ", "water color ",4,"휘인 (Whee In)",2021);	addQuestion(q557);
        Question q558 = new Question("RmuL-BPFi2Q", "사계 (Four Seasons)", "Weekend ", "불티 (Spark) ", "Make Me Love You ",2,"TAEYEON (태연)",2021);	addQuestion(q558);
        Question q559 = new Question("AIyarEsQvAg", "Sorry", "Sixteen (Feat. Changmo) ", "INSIDE OUT ", "WHERE YOU AT ",3,"NU'EST (뉴이스트)",2021);	addQuestion(q559);
        Question q560 = new Question("e70PkoJhQYM", "운전만해 (We Ride)", "치맛바람 (Chi Mat Ba Ram) ", "Summer Taste ", "BAAM ",2,"브레이브걸스(Brave Girls)",2021);	addQuestion(q560);
        Question q561 = new Question("86BST8NIpNM", "On the Ground", "strawberry moon ", "Coin ", "라일락 (LILAC) ",3,"IU(아이유)",2021);	addQuestion(q561);
        Question q562 = new Question("1JHOl9CSmXk", "마리아 (Maria)", "Cold Blooded ", "눈누난나 (NUNU NANA) ", "Gucci ",2,"Jessi (제시)",2021);	addQuestion(q562);
        Question q563 = new Question("c9RzZpV460k", "Psycho", "음파음파 (Umpah Umpah) ", "짐살라빔 (Zimzalabim) ", "Queendom ",4,"Red Velvet (레드벨벳)",2021);	addQuestion(q563);
        Question q564 = new Question("zLrWIgkvoB0", "에너제틱 (Energetic)", "PARANOIA ", "Antidote ", "뭐해 (What are you up to) ",2,"강다니엘(KANGDANIEL)",2021);	addQuestion(q564);
        Question q565 = new Question("CuklIb9d3fI", "Butter", "Dynamite ", "Permission to Dance ", "소리꾼 (Thunderous) ",3,"BTS (방탄소년단)",2021);	addQuestion(q565);
        Question q566 = new Question("Z3RA7bi5FUM", "DUN DUN", "LA DI DA ", "Pirate ", "FIRST ",4,"EVERGLOW (에버글로우)",2021);	addQuestion(q566);
        Question q567 = new Question("7uxu4Z2HAnA", "Cherry Bomb", "Superhuman ", "Sticker ", "Favorite (Vampire) ",4,"NCT 127 (엔시티 127)",2021);	addQuestion(q567);
        Question q568 = new Question("xBsHAyB73Rk", "Face ID", "Rosario ", "내 얘기 같아 (Based On A True Story) ", "술이 달다 (LOVEDRUNK) ",1,"EPIK HIGH (에픽하이)",2021);	addQuestion(q568);
        Question q569 = new Question("BtJMOVKjhUo", "Lo Siento (Feat. Leslie Grace)", "House Party ", "Jopping ", "Atlantis ",2,"SUPER JUNIOR (슈퍼주니어)",2021);	addQuestion(q569);
        Question q570 = new Question("-6aVQrzja9U", "Trauma", "O Sole Mio(오솔레미오)", "우리집 (My House)", "해야 해 (Make it)",4,"2PM",2021);	addQuestion(q570);
        Question q571 = new Question("AJPLgrfBiBo", "We don't talk together", "비가 오는 날엔 (2021)(On Rainy Day) ", "헤픈 우연 (HAPPEN) ", "Jenga (Feat. Gaeko) ",3,"헤이즈 (Heize)",2021);	addQuestion(q571);
        Question q572 = new Question("QPUjV7epJqE", "Hello Future", "맛 (Hot Sauce) ", "BOOM ", "RESONANCE ",1,"NCT DREAM (엔시티 드림)",2021);	addQuestion(q572);
        Question q573 = new Question("DZEOt4pQXXk", "Tag Me (@Me)", "Holiday Party ", "Zig Zag ", "After School ",2,"Weeekly(위클리)",2021);	addQuestion(q573);
        Question q574 = new Question("z3szNvgQxHo", "화(火花)(HWAA)", "Oh my god", "LA DI DA", "DUN DUN",1,"(여자)아이들((G)I-DLE)",2021);	addQuestion(q574);
        Question q575 = new Question("UZeWM-hfZBQ", "비가 내리는 날에는 (On A Rainy Day)", "My Love ", "별의 조각 (Stardust) ", "밤하늘의 별을 (Shiny Star) ",3,"YOUNHA(윤하)",2021);	addQuestion(q575);
        Question q576 = new Question("qfVuRQX0ydQ", "ASAP", "색안경 (STEREOTYPE) ", "Zig Zag ", "After School ",4,"Weeekly (위클리)",2021);	addQuestion(q576);
        Question q577 = new Question("vPwaXytZcgI", "SCIENTIST", "Alcohol-Free", "I CAN'T STOP ME ", "MORE & MORE ",1,"TWICE(트와이스)",2021);	addQuestion(q577);
        Question q578 = new Question("sQTGAggO0Gw", "The Stealer", "THRILL RIDE ", "MAVERICK ", "Giddy Up ",3,"THE BOYZ(더보이즈)",2021);	addQuestion(q578);
        Question q579 = new Question("qRdTyoZd3rg", "JEALOUSY", "FOLLOW ", "FANTASIA ", "Rush Hour ",4,"MONSTA X (몬스타엑스)",2021);	addQuestion(q579);
        Question q580 = new Question("1HesnepeVkU", "Trauma", "O Sole Mio(오솔레미오) ", "FLASH ", "해야 해 (Make it) ",1,"SF9",2021);	addQuestion(q580);
        Question q581 = new Question("57n4dZAPxNY", "Christmas EveL", "Hellevator ", "Easy ", "소리꾼 (Thunderous) ",1,"Stray Kids (스트레이 키즈)",2021);	addQuestion(q581);
        Question q582 = new Question("H8kqPkEXP_E", "Birthday", "What You Waiting For ", "DUMB DUMB ", "XOXO ",4,"JEON SOMI (전소미)",2021);	addQuestion(q582);
        Question q583 = new Question("mLCsbacHxA8", "랑데부 (Rendezvous)", "Counting Stars (feat. Beenzino) ", "BAD LOVE ", "연애소설 (LOVE STORY) ",2,"BE'O (비오)",2021);	addQuestion(q583);
        Question q584 = new Question("VAEEblk-qDU", "몸 (BODY)", "도망가 (Run away) ", "탕!♡ (TANG!♡) ", "아낙네 (FIANCÉ) ",3,"MINO",2021);	addQuestion(q584);
        Question q585 = new Question("be5yMhqtdyQ", "빔밤붐 (BIM BAM BUM)", "Ring Ring ", "Why Not ", "PTT (Paint The Town) ",4,"LOONA (이달의 소녀)",2021);	addQuestion(q585);
        Question q586 = new Question("7tkbzxa8MFQ", "ELEVEN", "환상동화 (Secret Story of the Swan) ", "FIESTA ", "비올레타 (Violeta) ",1,"IVE (아이브)",2021);	addQuestion(q586);
        Question q587 = new Question("oVPYa7QCmRg", "LAST DANCE", "선물 (Gift) ", "무제 (Untitled, 2014) ", "Don't(그러지 마) feat. RM ",4,"eAeon(이이언)",2021);	addQuestion(q587);
        Question q588 = new Question("f5_wn8mexmM", "SCIENTIST", "The Feels ", "I CAN'T STOP ME ", "MORE & MORE ",2,"TWICE(트와이스)",2021);	addQuestion(q588);
        Question q589 = new Question("9oyodEkzn94", "SWIPE", "마.피.아. In the morning ", "LOCO ", "비올레타 (Violeta) ",1,"ITZY(있지)",2021);	addQuestion(q589);
        Question q590 = new Question("H69tJmsgd9I", "Dreams Come True", "Savage", "ICY", "Not Shy",1,"aespa (에스파)",2021);	addQuestion(q590);
        Question q591 = new Question("wog1R1d4zls", "Not Shy", "Savage", "Next Level", "약속 (Forever)",4,"aespa (에스파)",2021);	addQuestion(q591);
        Question q592 = new Question("-2X-pL06628", "DUN DUN", "LA DI DA ", "Pirate ", "FIRST ",3,"EVERGLOW (에버글로우)",2021);	addQuestion(q592);
        Question q593 = new Question("z2KMxSb4GIo", "파랗게 (Love Me Harder)", "BUMP BUMP ", "FEEL LIKE ", "WAITING ",3,"WOODZ (조승연)",2021);	addQuestion(q593);
        Question q594 = new Question("eyP6iVQlnFo", "파랗게 (Love Me Harder)", "BUMP BUMP ", "FEEL LIKE ", "WAITING ",4,"WOODZ (조승연)",2021);	addQuestion(q594);
        Question q595 = new Question("80WvAnsHOdM", "야간비행 (Turbulence)", "WONDERLAND ", "ONE ", "After Midnight ",1,"ATEEZ (에이티즈)",2021);	addQuestion(q595);
        Question q596 = new Question("yY13X0BKaUw", "Rock with you", "Alligator ", "Shoot Out ", "GAMBLER ",4,"MONSTA X (몬스타엑스)",2021);	addQuestion(q596);
        Question q597 = new Question("ctAmUvoDkXE", "Cereal (Feat.ZICO)", "멋지게 인사하는 법(Hello Tutorial) ", "우아해 (woo ah) ", "선물을 고르며 (A Gift!) ",4,"Zion.T",2021);	addQuestion(q597);
        Question q598 = new Question("poiZpOXZXN8", "아로하 (Aloha)", "좋아좋아 (I Like You) ", "가을 타나 봐 (Fall in Fall) ", "비와 당신 (Rain and You) ",2,"조정석 (CHO JUNG SEOK)",2021);	addQuestion(q598);
        Question q599 = new Question("lNvBbh5jDcA", "여우야 (Yeowooya)", "괜찮아, 난 (I'm OK) (Feat. 이현우 (Lee Hyun Woo)) ", "좋은 사람 있으면 소개시켜줘 (Introduce me a good person) ", "안녕 (Hello) ",4,"조이 (JOY)",2021);	addQuestion(q599);
        Question q600 = new Question("URRimPZBHf8", "너에게 난, 나에게 넌 (Me to You, You to Me)", "슈퍼스타 (Superstar) ", "사랑하게 될 줄 알았어 (I Knew I Love) ", "여우야 (Yeowooya) ",2,"미도와 파라솔 (Mido and Falasol)",2021);	addQuestion(q600);
        Question q601 = new Question("2anE5ekyQKY", "몰랐니 (Lil' Touch)", "Fire Saturday (불토) ", "I'll be yours ", "Holiday ",2,"SECRET NUMBER (시크릿넘버)",2021);	addQuestion(q601);
        Question q602 = new Question("gjyEcSim4js", "야 하고 싶어 (CALL YOU BAE) Feat. XIUMIN(시우민) of EXO", "PADO ", "신청곡 (Song request) ", "어른 (Grown Ups) ",2,"BIBI (비비)",2021);	addQuestion(q602);
        Question q603 = new Question("p_C_ZmSWpzc", "BUTTERFLY", "Easy (이지) ", "HAPPY ", "비밀이야 (Secret) ",2,"우주소녀 더 블랙 (WJSN THE BLACK)",2021);	addQuestion(q603);
        Question q604 = new Question("--zku6TB5NY", "LAST DANCE", "센치해(SENTIMENTAL) ", "아예 (AH YEAH) ", "꽃 길 (Flower Road) ",1,"BIGBANG",2016);	addQuestion(q604);
        Question q605 = new Question("-LVdNUn7zsM", "야 하고 싶어 (CALL YOU BAE) Feat. XIUMIN(시우민) of EXO", "PADO ", "신청곡 (Song request) ", "어른 (Grown Ups) ",1,"AOA 지민 (JIMIN)",2016);	addQuestion(q605);
        Question q606 = new Question("-e7CIlMRayY", "CALLING YOU", "불어온다 (NOT THE END) ", "사랑했나봐 (Loved) ", "걸어 (ALL IN) ",3,"Highlight(하이라이트)",2018);	addQuestion(q606);
        Question q607 = new Question("-OfOkiVFmhM", "BAD LOVE", "WANT ", "MOVE ", "Press Your Number ",2,"TAEMIN (태민)",2019);	addQuestion(q607);
        Question q608 = new Question("-EfjXQgE1e8", "Bambi", "UN Village ", "비가와 (Rain) ", "Dream ",2,"BAEKHYUN (백현)",2019);	addQuestion(q608);
        Question q609 = new Question("-Ih5UArd4zk", "Like Water", "봄인가 봐 (Spring Love) ", "RBB (Really Bad Boy) ", "Power Up ",1,"WENDY (웬디)",2021);	addQuestion(q609);



    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_SINGER, question.getSinger());
        cv.put(QuestionsTable.COLUMN_YEAR, question.getYear());
        db.insert(QuestionsTable.TABLE_NAME,null,cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setSinger(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_SINGER)));
                question.setYear(c.getInt(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_YEAR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public List<Question> getQuestions(String year) {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{year};
        Log.e("Year", " : " + selectionArgs.length);
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_YEAR + " = ?", selectionArgs);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setSinger(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_SINGER)));
                question.setYear(c.getInt(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_YEAR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public List<Question> getSingerQuestion(String singer) {
        List<Question> SingerquestionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] singerArgs = new String[]{singer};
        Log.e("Singer", " : " + singerArgs.length);
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_SINGER + " = ?", singerArgs);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setSinger(c.getString(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_SINGER)));
                question.setYear(c.getInt(c.getColumnIndexOrThrow(QuestionsTable.COLUMN_YEAR)));
                SingerquestionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return SingerquestionList;
    }
}
